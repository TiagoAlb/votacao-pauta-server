/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.teste.dbserver.votacaoPauta.jms;

/**
 *
 * @author Tiago
 */
import br.com.teste.dbserver.votacaoPauta.dao.AssociadoDAO;
import br.com.teste.dbserver.votacaoPauta.mail.SendMail;
import br.com.teste.dbserver.votacaoPauta.model.VotacaoStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageListenerComponent implements ApplicationRunner {

    @Autowired 
    JmsTemplate jmsTemplate;
    
    @Autowired
    JmsTemplate jmsTemplateTopic;
    
    @Autowired
    AssociadoDAO associadoDAO;
    
    @Autowired
    SendMail sendMail;

    @JmsListener(destination = "queue.sample")
    public void onReceiverQueue(VotacaoStatus votacaoStatus) {
        String[] mails = associadoDAO.findMailList();
        sendMail.send(mails, votacaoStatus);
    }

    @JmsListener(destination = "topic.sample", containerFactory = "jmsFactoryTopic")
    public void onReceiverTopic(String str) {
        System.out.println( str );
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        //jmsTemplate.convertAndSend("queue.sample", "{user: 'wolmir', usando: 'fila'}");
        //jmsTemplateTopic.convertAndSend("topic.sample", "{user: 'wolmir', usando: 'tópico'}");
    }
}
