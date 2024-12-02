package org.hyejung.security;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration // Servlet 의 ServletContext 를 이용하기 위해서, 스프링에서는 WebApplicationContext 라는 존재를 이용하기 위함.
@ContextConfiguration(locations = "file:**/*-context.xml")
@Log4j
public class MemberTests {

	@Autowired
	private PasswordEncoder pwencoder;
	
	@Autowired
	private DataSource ds;
	
	@Test
	public void testInsertMember() {
		String sql ="insert into tbl_member(userid,userpw,username) values(?,?,?)";
		
		for(int i=0 ; i <100; i++) {
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(2,pwencoder.encode("pw"+i));
				if(i<80) {
					pstmt.setString(1, "user"+i);
					pstmt.setString(3,"일반사용자"+i);
				}else if(i<90) {
					pstmt.setString(1, "manager"+i);
					pstmt.setString(3, "운영자"+i);
				}else {
					pstmt.setString(1, "admin"+i);
					pstmt.setString(3, "관리자"+i);
				}
				pstmt.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				if(pstmt!=null) {try {pstmt.close();} catch(Exception e) {}}
				if(con!=null) {try {con.close();} catch(Exception e) {}}
			}
		} // end for
		
	}
	
	@Test
	public void testInsertAuth() {
		String sql = "insert into tbl_member_auth (userid,auth) values (?,?)";
		
		for( int i = 0 ; i < 100; i ++) {
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(sql);
				
				if(i<80) {
					pstmt.setString(1, "user"+i);
					pstmt.setString(2, "ROLE_USER");
				}
				else if(i<90)
				{
					pstmt.setString(1, "manager"+i);
					pstmt.setString(2, "ROLE_MEMBER");
				}
				else {
					pstmt.setString(1, "admin"+i);
					pstmt.setString(2, "ROLE_ADMIN");
				}
				
				pstmt.executeUpdate();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			finally {
				if(pstmt!=null) {try {pstmt.close();} catch(Exception e) {}}
				if(con!=null) {try {con.close();} catch(Exception e) {}}
			}
		} // end for
	}
}
