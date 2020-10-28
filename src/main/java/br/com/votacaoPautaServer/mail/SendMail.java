/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.votacaoPautaServer.mail;

import br.com.votacaoPautaServer.model.Pauta;
import br.com.votacaoPautaServer.model.VotacaoStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 *
 * @author Tiago
 */
@Component
public class SendMail {
    
    @Autowired
    private JavaMailSender mailSender;
    
    public void send(
      String[] to, VotacaoStatus votacaoStatus) {
        try {
            SimpleMailMessage message = new SimpleMailMessage(); 
            StringBuilder mailMessage = new StringBuilder();
            Pauta pauta = votacaoStatus.getVotacao().getPauta();
            
            mailMessage.append("DETALHES DA PAUTA:")
                .append("\n").append("Título: ").append(pauta.getTitulo())
                .append("\n").append("Descricao: ").append(pauta.getDescricao())
                .append("\n").append("\n").append("RESULTADO DA VOTAÇÃO:")
                .append("\n").append(votacaoStatus.getResultado())
                .append("\n").append("Votos SIM: ").append(votacaoStatus.getQtdSim())
                .append("\n").append("Votos NÃO: ").append(votacaoStatus.getQtdNao())
                .append("\n").append("Total de Votos: ").append(votacaoStatus.getQtdVotos());
            
            String[] emails = null;
            message.setTo(to); 
            message.setFrom("votacaopautadbserver@gmail.com");
            message.setCc("votacaopautadbserver@gmail.com");
            message.setSubject("RESULTADO DA VOTAÇÃO PARA A PAUTA '" + votacaoStatus.getVotacao().getPauta().getTitulo() + "'"); 
            message.setText(mailMessage.toString());
            mailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
        }
    }
    
}
