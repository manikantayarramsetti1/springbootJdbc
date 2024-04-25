package com.learning.springbootJdbc.dao;

import com.learning.springbootJdbc.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonDao {
    @Autowired
    private DataSource dataSource;

    public void insertPerson(Person person) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DataSourceUtils.getConnection(dataSource);
            String sql = "INSERT INTO person (name, email) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, person.getName());
            stmt.setString(2, person.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(stmt);
            DataSourceUtils.releaseConnection(conn, dataSource);
        }
    }

    public Person selectPerson(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DataSourceUtils.getConnection(dataSource);
            String sql = "SELECT * FROM person WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return new Person(rs.getInt("id"), rs.getString("name"), rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(stmt);
            DataSourceUtils.releaseConnection(conn, dataSource);
        }
        return null;
    }

    public List<Person> selectAllPersons() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Person> persons = new ArrayList<>();
        try {
            conn = DataSourceUtils.getConnection(dataSource);
            String sql = "SELECT * FROM person";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                persons.add(new Person(rs.getInt("id"), rs.getString("name"), rs.getString("email")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(stmt);
            DataSourceUtils.releaseConnection(conn, dataSource);
        }
        return persons;
    }

    public boolean updatePerson(Person person) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DataSourceUtils.getConnection(dataSource);
            String sql = "UPDATE person SET name = ?, email = ? WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, person.getName());
            stmt.setString(2, person.getEmail());
            stmt.setInt(3, person.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(stmt);
            DataSourceUtils.releaseConnection(conn, dataSource);
        }
        return false;
    }

    public boolean deletePerson(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DataSourceUtils.getConnection(dataSource);
            String sql = "DELETE FROM person WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(stmt);
            DataSourceUtils.releaseConnection(conn, dataSource);
        }
        return false;
    }

    private void close(AutoCloseable ac) {
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
