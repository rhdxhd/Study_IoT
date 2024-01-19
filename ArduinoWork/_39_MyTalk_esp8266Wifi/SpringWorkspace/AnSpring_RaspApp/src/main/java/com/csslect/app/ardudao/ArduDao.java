package com.csslect.app.ardudao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.csslect.app.ardudto.ArduDto;

public class ArduDao {

	DataSource dataSource;

	public ArduDao() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:/comp/env/team01");
			/*dataSource = (DataSource) context.lookup("java:/comp/env/CSS");*/
		} catch (NamingException e) {
			e.getMessage();
		}

	}

	public ArrayList<ArduDto> arduGetLed() {		
		
		ArrayList<ArduDto> adtos = new ArrayList<ArduDto>();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;		
		
		try {
			connection = dataSource.getConnection();
			String query = "select * from arduino"
							+ " where (id, updatetime)" 
							+ " in (select id, max(updatetime) from arduino group by id)";
			
//			String query = "select id, name, value, updatetime"					
//							+ " from (select * from arduino order by updatetime desc)" 
//							+ " where ROWNUM <= 2";
			
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();
			
			while (resultSet.next()) {
				String id = resultSet.getString("id");
				String name = resultSet.getString("name");
				String value = resultSet.getString("value");
				String updatetime = resultSet.getString("updatetime"); 
				
				ArduDto adto = new ArduDto(id, name, value, updatetime);
				adtos.add(adto);			
			}	
			
			System.out.println("adtos크기" + adtos.size());
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		} finally {
			try {			
				
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return adtos;

	}
	
public ArrayList<ArduDto> arduSetLed(String id1, String value1) {
	
		String id = id1, name = "", value = value1;
		
		if(id.equals("CSSLed01")) {
			name = "LivingRoomLight";
		}else if(id.equals("CSSSec01")) {
			name = "Secom";
		}
		
		int state = -1;		
				
		ArrayList<ArduDto> adtos = new ArrayList<ArduDto>();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;		
		
		try {
			connection = dataSource.getConnection();
			String query = "insert into arduino(id, name, value, updatetime)"
					+ "values(?, ?,?, to_char(sysdate, 'yy/mm/dd hh24:mi:ss') )";

			prepareStatement = connection.prepareStatement(query);
			prepareStatement.setString(1, id);
			prepareStatement.setString(2, name);
			prepareStatement.setString(3, value);
			state = prepareStatement.executeUpdate();
			
			if (state > 0) {
				System.out.println(state + "삽입성공");				
			} else {
				System.out.println(state + "삽입실패");
			}
			
//			query = "insert into arduino(id, name, value, updatetime)"
//					+ "values(?, ?,?, to_char(sysdate, 'yy/mm/dd hh24:mi:ss') )";
//			+ "values('" + id1 + "', '" + name1 + "','" + value1 + "', to_char(sysdate) )";
//
//			prepareStatement = connection.prepareStatement(query);
//			prepareStatement.setString(1, id2);
//			prepareStatement.setString(2, name2);
//			prepareStatement.setString(3, value2);
//			state = prepareStatement.executeUpdate();
//			
//			if (state > 0) {
//				System.out.println(state + "삽입성공2");				
//			} else {
//				System.out.println(state + "삽입실패2");
//			}
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		} finally {
			try {			
				
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}	

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		return adtos;

	}
	
	/*public int anInsertMulti(int id, String name, String date, String dbImgPath, String uploadType, String videoImagePath) {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
				
		int state = -1;

		try {			
			// 
			connection = dataSource.getConnection();
			String query = "insert into test2(id, name, hire_date, image1, uploadtype, videoImagePath) " + "values(" + id + ",'" + name + "',"
					+ "to_date('" + date + "','rr/mm/dd') , '" + dbImgPath + "', '" + uploadType + "', '" + videoImagePath + "' )";

			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();
			
			if (state > 0) {
				System.out.println(state + "삽입성공");				
			} else {
				System.out.println(state + "삽입실패");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			} 

		}

		return state;

	}
	

	public int anUpdateMulti(int id, String name, String date, String dbImgPath, String uploadtype, String videoImagePath) {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		
		int state = -1;
	
		try {			
			// 아이디는 수정할수 없음			
			connection = dataSource.getConnection();
			String query = "update test2 set " 			             
		             + " name = '" + name + "' "
		             + ", hire_date = '" + date + "' "
		             + ", image1 = '" + dbImgPath + "' "
		             + ", uploadtype = '" + uploadtype + "' "
		             + ", videoImagePath = '" + videoImagePath + "' "
					 + " where id = " + id ;
			
			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();
	
			if (state > 0) {
				System.out.println("수정1성공");
				
			} else {
				System.out.println("수정1실패");
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
	
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
	
			}
		}
	
		return state;
	
	}
	
	public int anUpdateMultiNo(int id, String name, String date) {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		
		int state = -1;
	
		try {			
			// 아이디는 수정할수 없음			
			connection = dataSource.getConnection();
			String query = "update test2 set " 			             
		             + " name = '" + name + "' "
		             + ", hire_date = '" + date + "' "		             
					 + " where id = " + id ;
			
			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();
	
			if (state > 0) {
				System.out.println("수정2성공");
				
			} else {
				System.out.println("수정2실패");
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
	
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
	
			}
		}
	
		return state;
	}
	
	public int anDeleteMulti(int id) {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		
		int state = -1;

		try {
			connection = dataSource.getConnection();
			String query = "delete from test2 where id=" + id;
			
			System.out.println(id);

			prepareStatement = connection.prepareStatement(query);
			state = prepareStatement.executeUpdate();

			if (state > 0) {
				System.out.println("삭제성공");				
			} else {
				System.out.println("삭제실패");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return state;

	}
*/
		
	
	
}
