package com.hogglepot;

import java.sql.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class QueryStories {
	
	public QueryStories() {
		super();
	}

	public ConcurrentMap<String, Story> queryStoryList() {
		ConcurrentMap<String, Story> storyList = new ConcurrentHashMap<String, Story>();
		String cmd;
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (Exception e) {
			System.out.println("Unable to load driver.");
			e.printStackTrace();
		}
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String userid = "";
			String passwd = "";
			conn = DriverManager.getConnection(
					"jdbc:derby:F:/Java Training/db/hpsa",userid,passwd);
			stmt = conn.createStatement();
			try {
				cmd = ("SELECT SID, TITLE, DATE FROM Stories");
				rs = stmt.executeQuery(cmd);
				while (rs.next()) {
					String storyID = rs.getString("SID");
					String storyTitle = rs.getString("TITLE");
					String storyDate = rs.getString("DATE");
					Story story = new Story(storyID, storyTitle, storyDate);
					storyList.putIfAbsent(storyID, story);
				}
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return storyList;
	}
	
	public Story getStoryContents(String story_id) {
		Story story = new Story();
		String cmd;
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (Exception e) {
			System.out.println("Unable to load driver.");
			e.printStackTrace();
		}
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String userid = "";
			String passwd = "";
			conn = DriverManager.getConnection(
					"jdbc:derby:F:/Java Training/db/hpsa",userid,passwd);
			stmt = conn.createStatement();
			try {
				cmd = ("SELECT * FROM Stories WHERE SID=" + story_id);
				rs = stmt.executeQuery(cmd);
				while (rs.next()) {
					String storyID = rs.getString("SID");
					String storyTitle = rs.getString("TITLE");
					String storyDate = rs.getString("DATE");
					String storyContent = rs.getString("CONTENT");
					String authorID = rs.getString("AID");
					
					story.setStoryID(storyID);
					story.setStoryTitle(storyTitle);
					story.setStoryDate(storyDate);
					story.setStoryContent(storyContent);
					story.setAuthorID(authorID);
				}
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return story;
	}
	
	public int addStory(String storyTitle, String storyDate, String storyContent, String authorID) {
		int response = 1;
		String cmd;
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (Exception e) {
			System.out.println("Unable to load driver.");
			e.printStackTrace();
		}
		Connection conn = null;
		Statement stmt = null;
		int result = 0;
		try {
			String userid = "";
			String passwd = "";
			conn = DriverManager.getConnection(
					"jdbc:derby:F:/Java Training/db/hpsa",userid,passwd);
			stmt = conn.createStatement();
			try {
				String values = ("('" + storyTitle + "', '" + storyDate + "', '" + 
								storyContent + "', " + authorID + ")");
				cmd = ("INSERT INTO Stories (TITLE, DATE, CONTENT, AID) VALUES " + values);
				System.out.println(storyTitle);
				System.out.println(storyDate);
				System.out.println(storyContent);
				System.out.println(authorID);
				System.out.println("Query:" + cmd);
				result = stmt.executeUpdate(cmd);
				if (result == 1) {
					response = 0;
				}
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return response;
	}
		
}
