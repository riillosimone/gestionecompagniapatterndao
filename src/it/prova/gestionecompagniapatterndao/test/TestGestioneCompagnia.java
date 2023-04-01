package it.prova.gestionecompagniapatterndao.test;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import it.prova.gestionecompagniapatterndao.connection.MyConnection;
import it.prova.gestionecompagniapatterndao.dao.Constants;
import it.prova.gestionecompagniapatterndao.dao.compagnia.CompagniaDAO;
import it.prova.gestionecompagniapatterndao.dao.compagnia.CompagniaDAOImpl;
import it.prova.gestionecompagniapatterndao.dao.impiegato.ImpiegatoDAO;
import it.prova.gestionecompagniapatterndao.dao.impiegato.ImpiegatoDAOImpl;
import it.prova.gestionecompagniapatterndao.model.Compagnia;
import it.prova.gestionecompagniapatterndao.model.Impiegato;

public class TestGestioneCompagnia {

	public static void main(String[] args) {
		CompagniaDAO compagniaDAOInstance = null;
		ImpiegatoDAO impiegatoDAOInstance = null;

		// creo connection
		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			// ecco chi 'inietta' la connection: il chiamante
			compagniaDAOInstance = new CompagniaDAOImpl(connection);
			impiegatoDAOInstance = new ImpiegatoDAOImpl(connection);
			// INIZIO TEST

			// TEST CRUD COMPAGNIA
//			testInsertCompagnia(compagniaDAOInstance);
//			System.out.println("In tabella compagnia ci sono "+compagniaDAOInstance.list().size()+" elementi.");

//			testGetCompagnia(compagniaDAOInstance);
//			System.out.println("In tabella compagnia ci sono "+compagniaDAOInstance.list().size()+" elementi.");

//			System.out.println(compagniaDAOInstance.list());
//			TestUpdateCompagnia(compagniaDAOInstance);
//			System.out.println(compagniaDAOInstance.list());
//			System.out.println("In tabella compagnia ci sono "+compagniaDAOInstance.list().size()+" elementi.");

//			testDeleteCompagnia(compagniaDAOInstance);
//			System.out.println("In tabella compagnia ci sono " + compagniaDAOInstance.list().size() + " elementi.");

//			testFindByExample(compagniaDAOInstance);
//			System.out.println("In tabella compagnia ci sono "+compagniaDAOInstance.list().size()+" elementi.");

			// TEST CRUD IMPIEGATO
//			testInsertImpiegato(compagniaDAOInstance, impiegatoDAOInstance);
//			System.out.println("In tabella compagnia ci sono "+impiegatoDAOInstance.list().size()+" elementi.");

//			testGetImpiegato(impiegatoDAOInstance);
//			System.out.println("In tabella compagnia ci sono "+impiegatoDAOInstance.list().size()+" elementi.");

			TestUpdateImpiegato(impiegatoDAOInstance, compagniaDAOInstance);
			System.out.println("In tabella compagnia ci sono "+impiegatoDAOInstance.list().size()+" elementi.");

			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// METODI TEST

	// METODI TEST CRUD COMPAGNIA

	private static void testInsertCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testInsertCompagnia inizio......");
		int quantiElementiInseriti = compagniaDAOInstance
				.insert(new Compagnia("Mia Compagnia", 10000, LocalDate.now()));
		if (quantiElementiInseriti < 1)
			throw new RuntimeException("testInsertCompagnia : FAILED");
		System.out.println(".......testInsertCompagnia fine: PASSED......");
	}

	private static void testGetCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testGetCompagnia inizio......");
		List<Compagnia> elencoVociPresenti = compagniaDAOInstance.list();
		if (elencoVociPresenti.size() < 1)
			throw new RuntimeException("testGetCompagnia : FAILED, non ci sono voci sul DB");
		Compagnia primaVoceDellaLista = elencoVociPresenti.get(0);
		Compagnia compagniaDaCercare = compagniaDAOInstance.get(primaVoceDellaLista.getId());
		if (compagniaDaCercare == null
				|| !compagniaDaCercare.getRagioneSociale().equals(primaVoceDellaLista.getRagioneSociale()))
			throw new RuntimeException("testGetCompagnia : FAILED, le ragioni sociali non corrispondono.");
		System.out.println(compagniaDaCercare);
		System.out.println(".......testGetCompagnia fine: PASSED......");
	}

	private static void TestUpdateCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......TestUpdateCompagnia inizio......");
		List<Compagnia> elencoVociPresenti = compagniaDAOInstance.list();
		if (elencoVociPresenti.size() < 1)
			throw new RuntimeException("testGetCompagnia : FAILED, non ci sono voci sul DB");
		Compagnia compagniaDaAggiornare = elencoVociPresenti.get(0);
		String nuovoNomeCompagnia = "franco";
		System.out.println("...before update: " + compagniaDaAggiornare);
		compagniaDaAggiornare.setRagioneSociale(nuovoNomeCompagnia);
		compagniaDAOInstance.update(compagniaDaAggiornare);
		Long idCompagniaAggiornata = compagniaDaAggiornare.getId();
		Compagnia compagniaAggiornata = compagniaDAOInstance.get(idCompagniaAggiornata);
		System.out.println("...after update: " + compagniaAggiornata);
		if (compagniaAggiornata == null || !compagniaAggiornata.getRagioneSociale().equals(nuovoNomeCompagnia))
			throw new RuntimeException("TestUpdateCompagnia : FAILED");
		System.out.println(".......TestUpdateCompagnia fine: PASSED......");
	}

	private static void testDeleteCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testDeleteCompagnia inizio......");
		int quantiElementiInseriti = compagniaDAOInstance
				.insert(new Compagnia("Mia Compagnia", 10000, LocalDate.now()));
		if (quantiElementiInseriti < 1)
			throw new RuntimeException("testDeleteCompagnia : FAILED");
		List<Compagnia> elencoVociPresenti = compagniaDAOInstance.list();
		int numeroElementiPresentiPrimaDellaRimozione = elencoVociPresenti.size();
		if (numeroElementiPresentiPrimaDellaRimozione < 1)
			throw new RuntimeException("testDeleteUser : FAILED, non ci sono voci sul DB");
		Compagnia ultimaCompagniaDellaLista = elencoVociPresenti.get(numeroElementiPresentiPrimaDellaRimozione - 1);
		compagniaDAOInstance.delete(ultimaCompagniaDellaLista);
		int numeroElementiPresentiDopoLaRimozione = compagniaDAOInstance.list().size();
		if (numeroElementiPresentiDopoLaRimozione != numeroElementiPresentiPrimaDellaRimozione - 1)
			throw new RuntimeException("testDeleteUser : FAILED, la rimozione non è avvenuta");

		System.out.println(".......testDeleteCompagnia fine: PASSED......");
	}

	private static void testFindByExample(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testFindByExample inizio......");
		List<Compagnia> elencoVociPresenti = compagniaDAOInstance.list();
		if (elencoVociPresenti.size() < 1)
			throw new RuntimeException("testFindByExample : FAILED, non ci sono voci sul DB");
		Compagnia compagniaExample = new Compagnia("no");
		List<Compagnia> listaCompagniaLikeExample = compagniaDAOInstance.findByExample(compagniaExample);
		if (listaCompagniaLikeExample.size() < 1) {
			throw new RuntimeException("testFindByExample : FAILED, non ci sono voci sul DB");
		}
		System.out.println("Gli elementi della lista sono: " + listaCompagniaLikeExample.size());
		System.out.println(listaCompagniaLikeExample);
		System.out.println(".......testFindByExample fine: PASSED.............");
	}

	// METODI TEST CRUD IMPIEGATO
	private static void testInsertImpiegato(CompagniaDAO compagniaDAOInstance, ImpiegatoDAO impiegatoDAOInstance)
			throws Exception {
		System.out.println(".......testInsertImpiegato inizio......");
		// mi serve prima un negozio esistente
		List<Compagnia> elencoVociPresenti = compagniaDAOInstance.list();
		if (elencoVociPresenti.size() < 1)
			throw new RuntimeException("testGetCompagnia : FAILED, non ci sono voci sul DB");
		
		Compagnia compagniaDaAssegnare = elencoVociPresenti.get(0);
		LocalDate dataNascita = LocalDate.parse("1980-05-19");
		LocalDate dataAssunzione = LocalDate.parse("2023-03-19");
		int quantiElementiInseriti = impiegatoDAOInstance.insert(new Impiegato("Mario","Rossi","MRARSS80E19C351A",dataNascita,dataAssunzione,compagniaDaAssegnare));
		if (quantiElementiInseriti < 1)
			throw new RuntimeException("testInsertImpiegato : FAILED");
		List<Impiegato> listaImpiegati = impiegatoDAOInstance.list();
		System.out.println(listaImpiegati);
		System.out.println(".......testInsertImpiegato fine: PASSED......");
	}
	
	private static void testGetImpiegato(ImpiegatoDAO impiegatoDAOInstance) throws Exception {
		System.out.println(".......testGetImpiegato inizio......");
		List<Impiegato> elencoVociPresenti = impiegatoDAOInstance.list();
		if (elencoVociPresenti.size() < 1)
			throw new RuntimeException("testGetCompagnia : FAILED, non ci sono voci sul DB");
		Impiegato primaVoceDellaLista = elencoVociPresenti.get(0);
		Impiegato compagniaDaCercare = impiegatoDAOInstance.get(primaVoceDellaLista.getId());
		if (compagniaDaCercare == null
				|| !compagniaDaCercare.getNome().equals(primaVoceDellaLista.getNome()))
			throw new RuntimeException("testGetImpiegato : FAILED, le ragioni sociali non corrispondono.");
		System.out.println(compagniaDaCercare);
		System.out.println(".......testGetImpiegato fine: PASSED......");
	}
	private static void TestUpdateImpiegato(ImpiegatoDAO impiegatoDAOInstance, CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......TestUpdateImpiegato inizio......");
		List<Compagnia> elencoCompagniePresenti = compagniaDAOInstance.list();
		if (elencoCompagniePresenti.size() < 1)
			throw new RuntimeException("TestUpdateImpiegato : FAILED, non ci sono compagnia sul DB");
		List<Impiegato> elencoImpiegatiPresenti = impiegatoDAOInstance.list();
		if (elencoImpiegatiPresenti.size() < 1)
			throw new RuntimeException("TestUpdateImpiegato : FAILED, non ci sono impiegati sul DB");
		
		Impiegato impiegatoDaAggiornare = elencoImpiegatiPresenti.get(1);
		String nuovoNomeImpiegato = "MArio";
		Compagnia nuovaCompagniaDaAssegnare = elencoCompagniePresenti.get(2);
		System.out.println(nuovaCompagniaDaAssegnare);
		System.out.println("...before update: " + impiegatoDaAggiornare);
		impiegatoDaAggiornare.setNome(nuovoNomeImpiegato);
		impiegatoDaAggiornare.setCompagnia(nuovaCompagniaDaAssegnare);
		impiegatoDAOInstance.update(impiegatoDaAggiornare);
		Long idImpiegatoAggiornato = impiegatoDaAggiornare.getId();
		Impiegato impiegatoAggiornato = impiegatoDAOInstance.get(idImpiegatoAggiornato);
		System.out.println("...after update: " + impiegatoAggiornato);
		if (impiegatoAggiornato == null || !impiegatoAggiornato.getNome().equals(nuovoNomeImpiegato))
			throw new RuntimeException("TestUpdateImpiegato : FAILED");
		System.out.println(".......TestUpdateImpiegato fine: PASSED......");
		//problemi con update (Cambia le date in auomatico. Succede anche con testNegozio)
	}

}
