package br.com.alura;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

import br.com.alura.entidade.AgendamentoEmail;
import br.com.alura.servico.AgendamentoEmailServico;

@Singleton
// Transação lida automaticamente com os comandos como begin, commit, rollback
//@TransactionManagement(TransactionManagementType.CONTAINER)
public class AgendamentoEmailJob {
	
	@Inject
	private AgendamentoEmailServico agendamentoEmailServico;
	
	@Inject
	@JMSConnectionFactory("java:jboss/DefaultJMSConnectionFactory")
	private JMSContext context;
	
	@Resource(mappedName = "java:/jms/queue/EmailQueue")
	private Queue queue;

	@Schedule(hour = "*", minute = "*", second = "*/10")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public synchronized void enviarEmail() {
		List<AgendamentoEmail> listarPorNaoAgendado 
			= agendamentoEmailServico.listarPorNaoAgendado();
		listarPorNaoAgendado.forEach(emailNaoAgendado -> {
			context.createProducer().send(queue, emailNaoAgendado);
			agendamentoEmailServico.alterar(emailNaoAgendado);
		});
	}
}
