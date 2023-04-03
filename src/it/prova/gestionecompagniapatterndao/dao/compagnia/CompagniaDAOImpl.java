package it.prova.gestionecompagniapatterndao.dao.compagnia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.prova.gestionecompagniapatterndao.dao.AbstractMySQLDAO;
import it.prova.gestionecompagniapatterndao.model.Compagnia;

public class CompagniaDAOImpl extends AbstractMySQLDAO implements CompagniaDAO {

	public CompagniaDAOImpl(Connection connection) {
		super(connection);
	}

	public List<Compagnia> list() throws Exception {
		// controllo per connessione attiva
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		List<Compagnia> result = new ArrayList<>();

		try (Statement ps = connection.createStatement(); ResultSet rs = ps.executeQuery("select * from compagnia")) {

			while (rs.next()) {
				Compagnia compagniaTemp = new Compagnia();
				compagniaTemp.setRagioneSociale(rs.getString("ragionesociale"));
				compagniaTemp.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
				compagniaTemp.setDataFondazione(
						rs.getDate("datafondazione") != null ? rs.getDate("datafondazione").toLocalDate() : null);
				compagniaTemp.setId(rs.getLong("ID"));
				result.add(compagniaTemp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;

	}

	public Compagnia get(Long idInput) throws Exception {
		// controllo per connessione attiva
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		if (idInput == null || idInput < 1)
			throw new Exception("Valore di input non ammesso.");
		Compagnia result = new Compagnia();
		try (PreparedStatement ps = connection.prepareStatement("select * from compagnia where id=?")) {

			ps.setLong(1, idInput);

			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {
					result = new Compagnia();
					result.setRagioneSociale(rs.getString("ragionesociale"));
					result.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
					result.setDataFondazione(
							rs.getDate("datafondazione") != null ? rs.getDate("datafondazione").toLocalDate() : null);
					result.setId(rs.getLong("ID"));
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

	public int update(Compagnia input) throws Exception {
		// controllo per connessione attiva
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");
		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"update compagnia set ragionesociale=?, fatturatoannuo=?, datafondazione=? where id=?; ")) {
			ps.setString(1, input.getRagioneSociale());
			ps.setInt(2, input.getFatturatoAnnuo());
			ps.setDate(3, java.sql.Date.valueOf(input.getDataFondazione()));
			ps.setLong(4, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return result;
	}

	public int insert(Compagnia input) throws Exception {
		// controllo per connessione attiva
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		if (input == null)
			throw new Exception("Valore di input non ammesso.");
		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"insert into compagnia (ragionesociale, fatturatoannuo, datafondazione) values (?, ?, ?);")) {
			ps.setString(1, input.getRagioneSociale());
			ps.setInt(2, input.getFatturatoAnnuo());
			ps.setDate(3, java.sql.Date.valueOf(input.getDataFondazione()));
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return result;
	}

	public int delete(Compagnia input) throws Exception {
		// controllo per connessione attiva
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");
		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement("delete from compagnia where id=?;")) {
			ps.setLong(1, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return result;
	}

	public List<Compagnia> findByExample(Compagnia input) throws Exception {
		// controllo per connessione attiva
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		if (input == null)
			throw new Exception("Valore di input non ammesso.");
		List<Compagnia> result = new ArrayList<>();
		Compagnia compagniaTemp = null;

		String query = "select * from compagnia where id is not null ";
		if (input.getRagioneSociale() != null && !input.getRagioneSociale().isEmpty()) {
			query += " and ragionesociale like '" + input.getRagioneSociale() + "%' ";
		}
		if (input.getFatturatoAnnuo() >= 0) {
			query += " and fatturatoannuo > '" + input.getFatturatoAnnuo() + "' ";
		}
		if (input.getDataFondazione() != null) {
			query += " and datafondazione= '" + java.sql.Date.valueOf(input.getDataFondazione() + "' ");
		}
		try (Statement ps = connection.createStatement()) {
			ResultSet rs = ps.executeQuery(query);

			while (rs.next()) {
				compagniaTemp = new Compagnia();
				compagniaTemp.setRagioneSociale(rs.getString("ragionesociale"));
				compagniaTemp.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
				compagniaTemp.setDataFondazione(
						rs.getDate("datafondazione") != null ? rs.getDate("datafondazione").toLocalDate() : null);
				compagniaTemp.setId(rs.getLong("c.ID"));
				result.add(compagniaTemp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	public List<Compagnia> findAllByDataAssunzioneMaggioreDi(LocalDate data) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		if (data == null)
			throw new Exception("Valore di input non ammesso.");
		List<Compagnia> result = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(
				"select distinct c.* from compagnia c inner join impiegato i on c.id=i.compagnia_id where dataassunzione > ?;")) {

			ps.setDate(1, java.sql.Date.valueOf(data));

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					Compagnia compagniaTemp = new Compagnia();
					compagniaTemp.setRagioneSociale(rs.getString("ragionesociale"));
					compagniaTemp.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
					compagniaTemp.setDataFondazione(
							rs.getDate("datafondazione") != null ? rs.getDate("datafondazione").toLocalDate() : null);
					compagniaTemp.setId(rs.getLong("c.ID"));
					result.add(compagniaTemp);
				}
			} // niente catch

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return result;
	}

	public List<Compagnia> findAllByRagioneSocialeContiene(String ragioneSocialeInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		if (ragioneSocialeInput == null || ragioneSocialeInput.isBlank())
			throw new Exception("Valore di input non ammesso.");
		List<Compagnia> result = new ArrayList<>();
		try (PreparedStatement ps = connection
				.prepareStatement("select * from compagnia c  where c.ragionesociale like ?;")) {

			ps.setString(1, "%" + ragioneSocialeInput + "%");

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					Compagnia compagniaTemp = new Compagnia();
					compagniaTemp.setRagioneSociale(rs.getString("ragionesociale"));
					compagniaTemp.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
					compagniaTemp.setDataFondazione(
							rs.getDate("datafondazione") != null ? rs.getDate("datafondazione").toLocalDate() : null);
					compagniaTemp.setId(rs.getLong("c.ID"));
					result.add(compagniaTemp);
				}
			} // niente catch

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return result;

	}

	public List<Compagnia> findAllByCodFisImpiegatoContiene(String codFisInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		if (codFisInput == null || codFisInput.isBlank())
			throw new Exception("Valore di input non ammesso.");
		List<Compagnia> result = new ArrayList<>();
		try (PreparedStatement ps = connection
				.prepareStatement("select distinct c.* from compagnia c "
						+ "inner join impiegato i on c.id=i.compagnia_id where i.codicefiscale like ?;")) {

			ps.setString(1, "%" + codFisInput + "%");

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					Compagnia compagniaTemp = new Compagnia();
					compagniaTemp.setRagioneSociale(rs.getString("ragionesociale"));
					compagniaTemp.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
					compagniaTemp.setDataFondazione(
							rs.getDate("datafondazione") != null ? rs.getDate("datafondazione").toLocalDate() : null);
					compagniaTemp.setId(rs.getLong("c.ID"));
					result.add(compagniaTemp);
				}
			} // niente catchq

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return result;
	}

}
