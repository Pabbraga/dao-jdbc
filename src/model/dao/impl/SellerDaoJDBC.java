package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller dpt) {

    }

    @Override
    public void update(Seller dpt) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
              "SELECT seller.*, department.Name as DepName " +
                      "FROM seller INNER JOIN department " +
                      "ON seller.DepartmentId = department.Id " +
                      "WHERE seller.Id = ?"
            );

            st.setInt(1, id);
            rs = st.executeQuery();
            if(rs.next()) {
                Department dpt = instantiateDepartment(rs);
                return instantiateSeller(rs, dpt);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dpt = new Department();
        dpt.setId(rs.getInt("DepartmentId"));
        dpt.setName(rs.getString("DepName"));
        return dpt;
    }

    private Seller instantiateSeller(ResultSet rs, Department dpt) throws SQLException {
        Seller sel = new Seller();
        sel.setId(rs.getInt("Id"));
        sel.setName(rs.getString("Name"));
        sel.setEmail(rs.getString("Email"));
        sel.setBaseSalary(rs.getDouble("BaseSalary"));
        sel.setBirthDate(rs.getDate("BirthDate"));
        sel.setDepartment(dpt);
        return sel;
    }

    @Override
    public List<Seller> findAll() {
        return null;
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT seller.*, department.Name as DepName " +
                            "FROM seller INNER JOIN department " +
                            "ON seller.DepartmentId = department.Id " +
                            "WHERE DepartmentId = ? " +
                            "ORDER BY Name"
            );

            st.setInt(1, department.getId());
            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {

                Department dpt = map.get(rs.getInt("DepartmentId"));

                if (dpt == null) {
                    dpt = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dpt);
                }

                list.add(instantiateSeller(rs, dpt));
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
