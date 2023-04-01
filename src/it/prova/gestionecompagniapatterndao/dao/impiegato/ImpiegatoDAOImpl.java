package it.prova.gestionecompagniapatterndao.dao.impiegato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.prova.gestionecompagniapatterndao.dao.AbstractMySQLDAO;
import it.prova.gestionecompagniapatterndao.model.Compagnia;
import it.prova.gestionecompagniapatterndao.model.Impiegato;

public class ImpiegatoDAOImpl extends AbstractMySQLDAO implements ImpiegatoDAO {
	public ImpiegatoDAOImpl(Connection connection) {
		super(connection);
	}

	@Override
	public List<Impiegato> list() throws Exception {
		// controllo per connessione attiva
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		List<Impiegato> result = new ArrayList<>();

		try (Statement ps = connection.createStatement();
				ResultSet rs = ps
						.executeQuery("select * from impiegato i inner join compagnia c on c.id=i.compagnia_id;")) {

			while (rs.next()) {
				Impiegato impiegatoTemp = new Impiegato();
				impiegatoTemp.setNome(rs.getString("nome"));
				impiegatoTemp.setCognome(rs.getString("cognome"));
				impiegatoTemp.setCodiceFiscale(rs.getString("codicefiscale"));
				impiegatoTemp.setDataNascita(
						rs.getDate("datanascita") != null ? rs.getDate("datanascita").toLocalDate() : null);
				impiegatoTemp.setDataAssunzione(
						rs.getDate("dataassunzione") != null ? rs.getDate("dataassunzione").toLocalDate() : null);
				impiegatoTemp.setId(rs.getLong("ID"));

				Compagnia compagniaTemp = new Compagnia();
				compagniaTemp.setRagioneSociale(rs.getString("ragionesociale"));
				compagniaTemp.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
				compagniaTemp.setDataFondazione(
						rs.getDate("datafondazione") != null ? rs.getDate("datafondazione").toLocalDate() : null);
				compagniaTemp.setId(rs.getLong("ID"));

				impiegatoTemp.setCompagnia(compagniaTemp);
				result.add(impiegatoTemp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public Impiegato get(Long idInput) throws Exception {
		// controllo per connessione attiva
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		if (idInput == null || idInput < 1)
			throw new Exception("Valore di input non ammesso.");
		Impiegato result = new Impiegato();
		try (PreparedStatement ps = connection.prepareStatement(
				"select * from impiegato i inner join compagnia c on c.id=i.compagnia_id where i.id=?")) {

			ps.setLong(1, idInput);

			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {
					result = new Impiegato();
					result.setNome(rs.getString("nome"));
					result.setCognome(rs.getString("cognome"));
					result.setCodiceFiscale(rs.getString("codicefiscale"));
					result.setDataNascita(
							rs.getDate("datanascita") != null ? rs.getDate("datanascita").toLocalDate() : null);
					result.setDataAssunzione(
							rs.getDate("dataassunzione") != null ? rs.getDate("dataassunzione").toLocalDate() : null);
					result.setId(rs.getLong("i.ID"));

					Compagnia compagniaTemp = new Compagnia();
					compagniaTemp.setRagioneSociale(rs.getString("ragionesociale"));
					compagniaTemp.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
					compagniaTemp.setDataFondazione(
							rs.getDate("datafondazione") != null ? rs.getDate("datafondazione").toLocalDate() : null);
					compagniaTemp.setId(rs.getLong("c.id"));

					result.setCompagnia(compagniaTemp);

				} else {
					result = null;
				}
			} // niente catch

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return result;
	}

	@Override
	public int update(Impiegato input) throws Exception {
		// controllo per connessione attiva
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");
		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"update impiegato set nome=?, cognome=?, codicefiscale=?, datanascita=?, dataassunzione=?, compagnia_id=? where id=?; ")) {
			ps.setString(1, input.getNome());
			ps.setString(2, input.getCognome());
			ps.setString(3, input.getCodiceFiscale());
			ps.setDate(4, java.sql.Date.valueOf(input.getDataNascita()));
			ps.setDate(5, java.sql.Date.valueOf(input.getDataAssunzione()));
			ps.setLong(6, input.getCompagnia().getId());
			ps.setLong(7, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return result;
	}

	@Override
	public int insert(Impiegato input) throws Exception {
		// controllo per connessione attiva
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		if (input == null)
			throw new Exception("Valore di input non ammesso.");
		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"insert into impiegato (nome, cognome, codicefiscale, datanascita, dataassunzione, compagnia_id) values (?, ?, ?, ?, ?, ?);")) {
			ps.setString(1, input.getNome());
			ps.setString(2, input.getCognome());
			ps.setString(3, input.getCodiceFiscale());
			ps.setDate(4, java.sql.Date.valueOf(input.getDataNascita()));
			ps.setDate(5, java.sql.Date.valueOf(input.getDataAssunzione()));
			ps.setLong(6, input.getCompagnia().getId());

			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return result;
	}

	@Override
	public int delete(Impiegato input) throws Exception {
		// controllo per connessione attiva
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");
		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement("delete from impiegato where id=?;")) {
			ps.setLong(1, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return result;
	}

	@Override
	public List<Impiegato> findByExample(Impiegato input) throws Exception {
		// controllo per connessione attiva
				if (isNotActive())
					throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
				if (input == null)
					throw new Exception("Valore di input non ammesso.");
				List<Impiegato> result = new ArrayList<>();
				Impiegato impiegatoTemp = null;

				String query = "select * from impiegato where id is not null ";
				if (input.getNome() != null && !input.getNome().isEmpty()) {
					query += " and nome like '" + input.getNome() + "%' ";
				}
				if (input.getCognome() != null && !input.getCognome().isEmpty()) {
					query += " and cognome like '" + input.getCognome() + "%' ";
				}
				if (input.getCodiceFiscale() != null && !input.getCodiceFiscale().isEmpty()) {
					query += " and codicefiscale like '" + input.getCodiceFiscale() + "%' ";
				}
				if (input.getCodiceFiscale() != null && !input.getCodiceFiscale().isEmpty()) {
					query += " and codicefiscale like '" + input.getCodiceFiscale() + "%' ";
				}
				if (input.getDataNascita() != null) {
					query += " and datanascita= '" + java.sql.Date.valueOf(input.getDataNascita() + "' ");
				}
				if (input.getDataAssunzione() != null) {
					query += " and dataassunzione= '" + java.sql.Date.valueOf(input.getDataAssunzione() + "' ");
				}
				if (input.getCompagnia().getId() != null && input.getCompagnia().getId()<1) {
					query += " and codicefiscale like '" + input.getCodiceFiscale() + "%' ";
				}
				try (Statement ps = connection.createStatement()) {
					ResultSet rs = ps.executeQuery(query);
					
					while (rs.next()) {
						impiegatoTemp = new Impiegato();
						impiegatoTemp.setNome(rs.getString("nome"));
						impiegatoTemp.setCognome(rs.getString("cognome"));
						impiegatoTemp.setCodiceFiscale(rs.getString("codicefiscale"));
						impiegatoTemp.setDataNascita(
								rs.getDate("datanascita") != null ? rs.getDate("datanascita").toLocalDate() : null);
						impiegatoTemp.setDataAssunzione(
								rs.getDate("dataassunzione") != null ? rs.getDate("dataassunzione").toLocalDate() : null);
						impiegatoTemp.setId(rs.getLong("ID"));
						result.add(impiegatoTemp);
					}

				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
				return result;
	}

	@Override
	public List<Impiegato> findAllByCompagnia(Compagnia compagnia) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countByDataFondazioneCompagniaGreaterThan(LocalDate data) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Impiegato> findAllByCompagniaConFatturatoMaggioreDi(int fatturatoInput) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Impiegato> findAllErroriAssunzione() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
