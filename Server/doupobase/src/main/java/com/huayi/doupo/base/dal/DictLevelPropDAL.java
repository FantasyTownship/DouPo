package com.huayi.doupo.base.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.io.InputStream;

import java.sql.PreparedStatement;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import com.huayi.doupo.base.dal.base.DALFather;
import com.huayi.doupo.base.model.player.PlayerMemObj;
import com.huayi.doupo.base.model.DictLevelProp;

public class DictLevelPropDAL extends DALFather {
	@SuppressWarnings("rawtypes")
	public class ItemMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			DictLevelProp dictLevelProp = new DictLevelProp();
			dictLevelProp.setId(rs.getInt("id"), 0);
			dictLevelProp.setFleetExp(rs.getInt("fleetExp"), 0);
			dictLevelProp.setGold(rs.getInt("gold"), 0);
			dictLevelProp.setCopper(rs.getInt("copper"), 0);
			dictLevelProp.setEnergy(rs.getInt("energy"), 0);
			dictLevelProp.setVigor(rs.getInt("vigor"), 0);
			dictLevelProp.setInTeamCard(rs.getInt("inTeamCard"), 0);
			dictLevelProp.setBenchCard(rs.getInt("benchCard"), 0);
			dictLevelProp.setPartnerCard(rs.getInt("partnerCard"), 0);
			dictLevelProp.setOneWarExp(rs.getInt("oneWarExp"), 0);
			dictLevelProp.setDuelFleetExp(rs.getInt("duelFleetExp"), 0);
			dictLevelProp.setDuelFleetCopper(rs.getInt("duelFleetCopper"), 0);
			dictLevelProp.setLootFleetExp(rs.getInt("lootFleetExp"), 0);
			dictLevelProp.setLootFleetCopper(rs.getInt("lootFleetCopper"), 0);
			dictLevelProp.setOneEliteWarExp(rs.getInt("oneEliteWarExp"), 0);
			dictLevelProp.setDescription(rs.getString("description"), 0);
			dictLevelProp.setVersion(rs.getInt("version"), 0);
			return dictLevelProp;
		}
	}

	public DictLevelProp add(final DictLevelProp model, int instPlayerId) throws Exception {
		try {
			StringBuilder strSql = new StringBuilder();
			strSql.append(" insert into Dict_Level_Prop (");
			strSql.append("fleetExp,gold,copper,energy,vigor,inTeamCard,benchCard,partnerCard,oneWarExp,duelFleetExp,duelFleetCopper,lootFleetExp,lootFleetCopper,oneEliteWarExp,description,version");
			strSql.append(" )");
			strSql.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

			final String sql = strSql.toString();
			KeyHolder keyHolder = new GeneratedKeyHolder();

			this.getJdbcTemplate().update(new PreparedStatementCreator(){
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setInt(1, model.getFleetExp());
					ps.setInt(2, model.getGold());
					ps.setInt(3, model.getCopper());
					ps.setInt(4, model.getEnergy());
					ps.setInt(5, model.getVigor());
					ps.setInt(6, model.getInTeamCard());
					ps.setInt(7, model.getBenchCard());
					ps.setInt(8, model.getPartnerCard());
					ps.setInt(9, model.getOneWarExp());
					ps.setInt(10, model.getDuelFleetExp());
					ps.setInt(11, model.getDuelFleetCopper());
					ps.setInt(12, model.getLootFleetExp());
					ps.setInt(13, model.getLootFleetCopper());
					ps.setInt(14, model.getOneEliteWarExp());
					ps.setString(15, model.getDescription());
					ps.setInt(16, 0);
					return ps;
				}
			},keyHolder);

			model.setId(keyHolder.getKey().intValue());
			model.setVersion(0);
			PlayerMemObj playerMemObj = getPlayerMemObjByPlayerId(instPlayerId);
			if (instPlayerId != 0 && isUseCach() && playerMemObj != null) {
				playerMemObj.dictLevelPropMap.put(model.getId(), model);
			}
		} catch (Exception e) {
			throw e;
		}
		return model;
	}

	public void batchAdd (final List<DictLevelProp> list) {
		StringBuilder sql = new StringBuilder();
		sql.append(" insert into Dict_Level_Prop (");
		sql.append("fleetExp,gold,copper,energy,vigor,inTeamCard,benchCard,partnerCard,oneWarExp,duelFleetExp,duelFleetCopper,lootFleetExp,lootFleetCopper,oneEliteWarExp,description,version");
		sql.append(" )");
		sql.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter (){
			public void setValues(PreparedStatement ps, int i) throws SQLException{
				DictLevelProp model = (DictLevelProp)list.get(i);
					ps.setInt(1, model.getFleetExp());
					ps.setInt(2, model.getGold());
					ps.setInt(3, model.getCopper());
					ps.setInt(4, model.getEnergy());
					ps.setInt(5, model.getVigor());
					ps.setInt(6, model.getInTeamCard());
					ps.setInt(7, model.getBenchCard());
					ps.setInt(8, model.getPartnerCard());
					ps.setInt(9, model.getOneWarExp());
					ps.setInt(10, model.getDuelFleetExp());
					ps.setInt(11, model.getDuelFleetCopper());
					ps.setInt(12, model.getLootFleetExp());
					ps.setInt(13, model.getLootFleetCopper());
					ps.setInt(14, model.getOneEliteWarExp());
					ps.setString(15, model.getDescription());
					ps.setInt(16, 0);
			}
			public int getBatchSize(){
				return list.size();
			}
		};
		getJdbcTemplate().batchUpdate(sql.toString(), setter);
	}

	public int deleteById(int id, int instPlayerId) throws Exception {
		try {
			PlayerMemObj playerMemObj = getPlayerMemObjByPlayerId(instPlayerId);
			if (instPlayerId != 0 && isUseCach() && playerMemObj != null) {
				playerMemObj.dictLevelPropMap.remove(id);
			}
			String sql = "delete from Dict_Level_Prop  where id = ?";
			Object[] params = new Object[]{id};
			return this.getJdbcTemplate().update(sql, params);
		} catch (Exception e) {
			throw e;
		}
	}

	public int deleteByWhere(String strWhere) throws Exception {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("delete from Dict_Level_Prop where 1=1 ");
			if (strWhere != null && !strWhere.equals("")) {
				sql.append(" and " + strWhere);
			}
			return this.getJdbcTemplate().update(sql.toString());
		} catch (Exception e) {
			throw e;
		}
	}

	public int update(String sql) throws Exception {
		try {
			return this.getJdbcTemplate().update(sql.toString());
		} catch (Exception e) {
			throw e;
		}
	}

	public DictLevelProp update(DictLevelProp model, int instPlayerId) throws Exception {
		try {
			Object[] params = null;
			int version = model.getVersion() + 1;
			StringBuffer sql = new StringBuffer("update Dict_Level_Prop set ");
			sql.append("fleetExp=?,gold=?,copper=?,energy=?,vigor=?,inTeamCard=?,benchCard=?,partnerCard=?,oneWarExp=?,duelFleetExp=?,duelFleetCopper=?,lootFleetExp=?,lootFleetCopper=?,oneEliteWarExp=?,description=?,version=? ");
			sql.append("where id=? and version=?");
			params = new Object[] { model.getFleetExp(),model.getGold(),model.getCopper(),model.getEnergy(),model.getVigor(),model.getInTeamCard(),model.getBenchCard(),model.getPartnerCard(),model.getOneWarExp(),model.getDuelFleetExp(),model.getDuelFleetCopper(),model.getLootFleetExp(),model.getLootFleetCopper(),model.getOneEliteWarExp(),model.getDescription(),version , model.getId(), model.getVersion() };
			int count = this.getJdbcTemplate().update(sql.toString(), params);
			if (count == 0) {
				throw new Exception("Concurrent Exception");
			} else {
				model.setVersion(version, 0);
				PlayerMemObj playerMemObj = getPlayerMemObjByPlayerId(instPlayerId);
				if (instPlayerId != 0 && isUseCach() && playerMemObj != null) {
					playerMemObj.dictLevelPropMap.put(model.getId(), model);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return model;
	}

	@SuppressWarnings("unchecked")
	public DictLevelProp getModel(int id, int instPlayerId) {
		try {
			PlayerMemObj playerMemObj = getPlayerMemObjByPlayerId(instPlayerId);
			if (instPlayerId != 0 && isUseCach() && playerMemObj != null) {
				DictLevelProp model = playerMemObj.dictLevelPropMap.get(id);
				if (model == null) {
					String sql = "select * from Dict_Level_Prop where id=?";
					Object[] params = new Object[]{id};
					playerMemObj.dictLevelPropMap.put(id, (DictLevelProp) this.getJdbcTemplate().queryForObject(sql, params, new ItemMapper()));
				} else {
					int cacheVersion = model.getVersion();
					List<Map<String, Object>> list = sqlHelper("select version from Dict_Level_Prop where id = " + id);
					 int dbVersion = (int)list.get(0).get("version");
					if (cacheVersion != dbVersion) {
						String sql = "select * from Dict_Level_Prop where id=?";
						Object[] params = new Object[]{id};
						playerMemObj.dictLevelPropMap.put(id, (DictLevelProp) this.getJdbcTemplate().queryForObject(sql, params, new ItemMapper()));
					}
				}
				model = playerMemObj.dictLevelPropMap.get(id);
				model.result = "";
				return model;
			} else {
				String sql = "select * from Dict_Level_Prop where id=?";
				Object[] params = new Object[]{id};
				DictLevelProp model = ( DictLevelProp) this.getJdbcTemplate().queryForObject(sql, params, new ItemMapper());
				model.result = "";
				return model;
			}
		} catch (DataAccessException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<DictLevelProp> getList(String strWhere, int instPlayerId) {
		StringBuffer sql = null;
		PlayerMemObj playerMemObj = getPlayerMemObjByPlayerId(instPlayerId);
		if (instPlayerId != 0 && isUseCach() && playerMemObj != null) {
			sql = new StringBuffer("select id, version from Dict_Level_Prop ");
		}else {
			sql = new StringBuffer("select * from Dict_Level_Prop ");
		}
		if (strWhere != null && !strWhere.equals("")) {
			sql.append(" where " + strWhere);
		}
		if (instPlayerId != 0 && isUseCach() && playerMemObj != null) {
			return listCacheCommonHandler(sql.toString(), instPlayerId);
		} else {
			List<DictLevelProp> dictLevelPropList = (List<DictLevelProp>) this.getJdbcTemplate().query(sql.toString(), new ItemMapper());
			return dictLevelPropList;
		}
	}

	public List<Long> getListIdByWhere(String strWhere) throws Exception {
		try {
			List<Long> listLong = new ArrayList<Long>();
			StringBuffer sql = new StringBuffer("select id from Dict_Level_Prop ");
			if (strWhere != null && !strWhere.equals("")) {
				sql.append(" where " + strWhere);
			}
			SqlRowSet rsSet = this.getJdbcTemplate().queryForRowSet(sql.toString());
			while (rsSet.next()) {
				listLong.add(rsSet.getLong("id"));
			}
			return listLong;
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public List<DictLevelProp> getListPagination(int index, int size, String strWhere, int instPlayerId) throws Exception {
		try {
			StringBuffer sql = null;
			PlayerMemObj playerMemObj = getPlayerMemObjByPlayerId(instPlayerId);
			if (instPlayerId != 0 && isUseCach() && playerMemObj != null) {
				sql = new StringBuffer("select id, version from Dict_Level_Prop ");
			}else {
				sql = new StringBuffer("select * from Dict_Level_Prop ");
			}
			if(index <= 0 || size <= 0){
				throw new Exception("index or size must bigger than zero");
			}else{
				index = (index - 1) * size;
			}
			if (strWhere != null && !strWhere.equals("")) {
				sql.append(" where " + strWhere);
			}
			sql.append(" limit " + index + "," + size + "");
			if (instPlayerId != 0 && isUseCach() && playerMemObj != null) {
				return listCacheCommonHandler(sql.toString(), instPlayerId);
			} else {
				return (List<DictLevelProp>) this.getJdbcTemplate().query(sql.toString(), new ItemMapper());
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public boolean isExist(long id, String strWhere) throws Exception {
		try {
			StringBuffer sql = new StringBuffer("select count(*) from Dict_Level_Prop where id =?");
			if (strWhere != null && !strWhere.equals("")) {
				sql.append(" or " + strWhere);
			}
			int count = this.getJdbcTemplate().queryForObject(sql.toString(), Integer.class);
			return count > 0 ? true : false;
		} catch (Exception e) {
			throw e;
		}
	}

	public int getCount(String strWhere) throws Exception {
		try {
			StringBuffer sql = new StringBuffer("select count(*) from Dict_Level_Prop");
			if (strWhere != null && !strWhere.equals("")) {
				sql.append(" where " + strWhere);
			}
			return this.getJdbcTemplate().queryForObject(sql.toString(), Integer.class);
		} catch (Exception e) {
			throw e;
		}
	}

	public List<Long> getCounts(String strWhere) throws Exception {
		try {
			List<Long> listLong = new ArrayList<Long>();
			StringBuffer sql = new StringBuffer("select count(*) as cnt from Dict_Level_Prop ");
			if (strWhere != null && !strWhere.equals("")) {
				sql.append(strWhere);
			}
			SqlRowSet rsSet = this.getJdbcTemplate().queryForRowSet(sql.toString());
			while (rsSet.next()) {
				listLong.add(rsSet.getLong("cnt"));
			}
			return listLong;
		} catch (Exception e) {
			throw e;
		}
	}

	public List<Map<String,Object>> sqlHelper(String sql) {
		return this.getJdbcTemplate().queryForList(sql);
	}

	@SuppressWarnings("unchecked")
	public List<DictLevelProp> getListFromMoreTale(String afterSql, int instPlayerId) {
		StringBuffer sql = null;
		PlayerMemObj playerMemObj = getPlayerMemObjByPlayerId(instPlayerId);
		if (instPlayerId != 0 && isUseCach() && playerMemObj != null) {
			sql = new StringBuffer("select a.id, a.version from Dict_Level_Prop a ");
		}else {
			sql = new StringBuffer("select a.* from Dict_Level_Prop a ");
		}
		if (afterSql != null && !afterSql.equals("")) {
			sql.append(afterSql);
		}
		if (instPlayerId != 0 && isUseCach() && playerMemObj != null) {
			return listCacheCommonHandler(sql.toString(), instPlayerId);
		} else {
			return (List<DictLevelProp>) this.getJdbcTemplate().query(sql.toString(), new ItemMapper());
		}
	}

	public List<Long> getListIdFromMoreTable(String afterSql) throws Exception {
		try {
			List<Long> listLong = new ArrayList<Long>();
			StringBuffer sql = new StringBuffer("select a.id from Dict_Level_Prop a ");
			if (afterSql != null && !afterSql.equals("")) {
				sql.append(afterSql);
			}
			SqlRowSet rsSet = this.getJdbcTemplate().queryForRowSet(sql.toString());
			while (rsSet.next()) {
				listLong.add(rsSet.getLong("id"));
			}
			return listLong;
		} catch (Exception e) {
			throw e;
		}
	}

	public int getStatResult(String statType, String conParam, String strWhere) throws Exception {
		int result = 0;
		try {
			StringBuffer sql = new StringBuffer("select "+statType+"("+conParam+") from  Dict_Level_Prop");
			if (strWhere != null && !strWhere.equals("")) {
				sql.append(" where " + strWhere);
			}
			return this.getJdbcTemplate().queryForObject(sql.toString(), Integer.class);
		} catch (Exception e) {
			return result;
		}
	}

	private List<DictLevelProp> listCacheCommonHandler(String sql, int instPlayerId){
		List<DictLevelProp> modelList = new ArrayList<DictLevelProp>();
		PlayerMemObj playerMemObj = getPlayerMemObjByPlayerId(instPlayerId);
		SqlRowSet rsSet = this.getJdbcTemplate().queryForRowSet(sql.toString());
		while (rsSet.next()) {
			int id = rsSet.getInt("id");
			int dbVersion = rsSet.getInt("version");
			DictLevelProp model = playerMemObj.dictLevelPropMap.get(id);
			if (model == null) {
				model = getModel(id, instPlayerId);
				model.result = "";
				modelList.add(model);
			} else {
				int cacheVersion = model.getVersion();
				if (cacheVersion != dbVersion) {
					model = getModel(id, instPlayerId);
				}
				model.result = "";
				modelList.add(model);
			}
		}
		return modelList;
	}

}