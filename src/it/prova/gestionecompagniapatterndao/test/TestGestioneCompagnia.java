package it.prova.gestionecompagniapatterndao.test;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import it.prova.gestionecompagniapatterndao.connection.MyConnection;
import it.prova.gestionecompagniapatterndao.dao.Constants;
import it.prova.gestionecompagniapatterndao.dao.compagnia.CompagniaDAO;
import it.prova.gestionecompagniapatterndao.dao.compagnia.CompagniaDAOImpl;
import it.prova.gestionecompagniapatterndao.model.Compagnia;

public class TestGestioneCompagnia {

	public static void main(String[] args) {
		CompagniaDAO compagniaDAOInstance = null;

		// creo connection
		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			// ecco chi 'inietta' la connection: il chiamante
			compagniaDAOInstance = new CompagniaDAOImpl(connection);
			
			
			
			//INIZIO TEST
//			testInsertCompagnia(compagniaDAOInstance);
//			System.out.println("In tabella compagnia ci sono "+compagniaDAOInstance.list().size()+" elementi.");
			
//			testGetCompagnia(compagniaDAOInstance);
//			System.out.println("In tabella compagnia ci sono "+compagniaDAOInstance.list().size()+" elementi.");

			System.out.println(compagniaDAOInstance.list());
			TestUpdateCompagnia(compagniaDAOInstance);
			System.out.println(compagniaDAOInstance.list());
			System.out.println("In tabella compagnia ci sono "+compagniaDAOInstance.list().size()+" elementi.");

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	//metodi test
	private static void testInsertCompagnia (CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testInsertCompagnia inizio......");
		int quantiElementiInseriti = compagniaDAOInstance
				.insert(new Compagnia("Mia Compagnia",10000,LocalDate.now()));
		if(quantiElementiInseriti<1)
			throw new RuntimeException("testInsertCompagnia : FAILED");
		System.out.println(".......testInsertCompagnia fine: PASSED......");
	}
	
	private static void testGetCompagnia (CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testGetCompagnia inizio......");
		List<Compagnia> elencoVociPresenti = compagniaDAOInstance.list();
		if(elencoVociPresenti.size()<1)
			throw new RuntimeException("testGetCompagnia : FAILED, non ci sono voci sul DB");
		Compagnia primaVoceDellaLista = elencoVociPresenti.get(0);
		Compagnia compagniaDaCercare = compagniaDAOInstance.get(primaVoceDellaLista.getId());
		if(compagniaDaCercare == null || !compagniaDaCercare.getRagioneSociale().equals(primaVoceDellaLista.getRagioneSociale()))
			throw new RuntimeException("testGetCompagnia : FAILED, le ragioni sociali non corrispondono.");
		System.out.println(compagniaDaCercare);
		System.out.println(".......testGetCompagnia fine: PASSED......");
	}
	
	
	private static void TestUpdateCompagnia (CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......TestUpdateCompagnia inizio......");
		List<Compagnia> elencoVociPresenti = compagniaDAOInstance.list();
		if(elencoVociPresenti.size()<1)
			throw new RuntimeException("testGetCompagnia : FAILED, non ci sono voci sul DB");
		Compagnia compagniaDaAggiornare = elencoVociPresenti.get(0);
		String nuovoNomeCompagnia ="Compagnia con nome nuovo";
		System.out.println("...before update: "+compagniaDaAggiornare);
		compagniaDaAggiornare.setRagioneSociale(nuovoNomeCompagnia);
		compagniaDAOInstance.update(compagniaDaAggiornare);
		Long idCompagniaAggiornata = compagniaDaAggiornare.getId();
		Compagnia compagniaAggiornata = compagniaDAOInstance.get(idCompagniaAggiornata);
		System.out.println("...after update: "+compagniaAggiornata);
		if(compagniaAggiornata == null || !compagniaAggiornata.getRagioneSociale().equals(nuovoNomeCompagnia))
			throw new RuntimeException("TestUpdateCompagnia : FAILED");
		System.out.println(".......TestUpdateCompagnia fine: PASSED......");
	}
	
}
