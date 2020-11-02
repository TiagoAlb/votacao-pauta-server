package br.com.votacaoPautaServer.controller;

import br.com.votacaoPautaServer.auth.AssociadoAuth;
import br.com.votacaoPautaServer.dao.AssociadoDAO;
import br.com.votacaoPautaServer.error.ApiError;
import br.com.votacaoPautaServer.error.ResourceNotFoundException;
import br.com.votacaoPautaServer.model.Associado;
import br.com.votacaoPautaServer.util.CpfCnpjUtils;
import static br.com.votacaoPautaServer.util.Util.VALIDATE_MAIL;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class Associados {
    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    public static final String SECRET
            = "lSlZbz79sUeirz-XNhY7hw6q6vqONlMEO51xw1OCLEjwnLWbpVo08kyKj-t6dThU4QE1Qtr26g_EClcZR8jCYUlg" +
              "w3f-AIZUfE-du1FoQSesnwI5JozjoaVUWGX7c7DGdgdAnGGToUQSr0SqHlfnhbEjDw3AbeyQ5DjUGRQVdUHXI_gQ";

    @Autowired
    AssociadoDAO associadoDAO;
    
    @RequestMapping(path = "/associados/login", method = RequestMethod.GET)
    public ResponseEntity<Associado> login(@AuthenticationPrincipal AssociadoAuth associadoAuth)
            throws IllegalArgumentException, UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        Calendar today = Calendar.getInstance();
        today.add(Calendar.HOUR, 6);
        Date expire = today.getTime();

        String token = JWT.create()
                .withClaim("id", associadoAuth.getAssociado().getId()).
                            withExpiresAt(expire).
                sign(algorithm);
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.set("token", token);

        return new ResponseEntity<>(associadoAuth.getAssociado(), respHeaders, HttpStatus.OK);
    }
    
    @RequestMapping(path = "/associados/validateLogin", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Associado> validateLogin(@AuthenticationPrincipal AssociadoAuth associadoAuth) {
        Optional<Associado> findById = associadoDAO.findById(associadoAuth.getAssociado().getId());
        if (findById.isPresent()) {
            return ResponseEntity.ok(findById.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/associados")
    public ResponseEntity<Object> postAssociado(@RequestBody Associado associado) {
        String cnpjCpfLimpo = CpfCnpjUtils.limpaCnpjCpf(associado.getCnpjCpf());

        try {
            Optional<Associado> associadoAtual = associadoDAO.findByCnpjCpf(cnpjCpfLimpo);
            if(associadoAtual.isPresent())
                throw new Exception("Este CNPJ ou CPF já está cadastrado!");
            
            if(!VALIDATE_MAIL(associado.getEmail()))
                throw new Exception("Email inválido!");
            
            associadoAtual = associadoDAO.findByEmail(associado.getEmail());
            if(associadoAtual.isPresent())
                throw new Exception("Este email já está cadastrado!");
            
            associado.setId(0);
            associado.setPassword(PASSWORD_ENCODER.encode(associado.getNewPassword()));
            associado.setCnpjCpf(cnpjCpfLimpo);
            associado.setCreation_date(new Date(System.currentTimeMillis()));
            ArrayList<String> permissions = new ArrayList<String>();
            permissions.add("user");
            associado.setPermissions(permissions);
        } catch(Exception e) {
            return new ResponseEntity<>(new ApiError(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "Erro ao incluir associado!", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(associadoDAO.save(associado), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/associados")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Associado> getAssociados(@RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "10") int page_results) {
        PageRequest pageRequest = PageRequest.of(page, page_results, Sort.unsorted());
        return associadoDAO.findAll(pageRequest);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/associados/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Associado getAssociado(@PathVariable long id) {
        Optional<Associado> associado = associadoDAO.findById(id);
        if (!associado.isPresent())
            throw new ResourceNotFoundException("Associado " + id + " não encontrado!");
             
        return associado.get();
    }
    
    @RequestMapping(method = RequestMethod.GET, path = "/associados/consultaCnpjCpf/{cnpjCPf}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getConsultaCNPJCPF(@PathVariable("cnpjCPf") String cnpjCpf) throws IOException {
        StringBuffer content = new StringBuffer();
        
        URL url = new URL("https://user-info.herokuapp.com/users/" + cnpjCpf);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET");
        con.setDoOutput(true);
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
  
        try (
            BufferedReader in = new BufferedReader(
            new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
        } catch(IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(content, HttpStatus.OK);
    }
}