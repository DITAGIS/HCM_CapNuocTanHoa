package com.ditagis.hcm.docsotanhoa.conectDB;

import com.ditagis.hcm.docsotanhoa.entities.HoaDon;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDB extends AbstractDB implements IDB<HoaDon, Boolean, String> {
    private final String TABLE_NAME = "HOADON";
    private final String SQL_SELECT = "SELECT ID,KHU,DOT,DANHBO,CULY,HOPDONG,TENKH,SONHA,DUONG,GIABIEU,DINHMUC,KY,NAM,CODE,CODEFU,CSCU,CSMOI,QUAN,PHUONG,MLT FROM " + TABLE_NAME;
    private final String SQL_SELECT_DANHBO = "SELECT DANHBO FROM " + TABLE_NAME;
    private final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " VALUES(?,?,?,?,?)";
    private final String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET CSC=? WHERE CSM=?";
    private final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE ClassId=?";
    private final String SQL_FIND = "SELECT * FROM " + TABLE_NAME + " WHERE ID=?";

    @Override
    public Boolean add(HoaDon e) {
        Connection cnn = this.condb.getConnect();
        String sql = this.SQL_INSERT;

        try {
            Statement st = cnn.createStatement();
            int result = st.executeUpdate(sql);
            st.close();
            cnn.close();
            return result > 0;

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean delete(String k) {
        Connection cnn = this.condb.getConnect();
        try {
            PreparedStatement pst = cnn.prepareStatement(this.SQL_DELETE);
            pst.setString(1, k);
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean update(HoaDon e) {
        Connection cnn = this.condb.getConnect();
        try {
            CallableStatement cbs = cnn.prepareCall(this.SQL_UPDATE);
            cbs.setString(1, e.getChiSoCu());
            cbs.setString(2, e.getChiSoMoi());
            return cbs.executeUpdate() > 0;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    @Override
    public HoaDon find(String k) {
//		Connection cnn = this.condb.getConnect();
//		HoaDon course = null;
//		try {
//			PreparedStatement pst = cnn.prepareStatement(this.SQL_FIND);
//			pst.setString(1, k);
//			ResultSet rs = pst.executeQuery();
//			if (rs.next()) {
//				String courseName = rs.getString(2);
//				course = new HoaDon(k, courseName);
//			}
//			rs.close();
//			pst.close();
//			cnn.close();
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return course;
        return null;
    }

    @Override
    public List<HoaDon> getAll() {
        List<HoaDon> result = new ArrayList<HoaDon>();
        Connection cnn = this.condb.getConnect();
        try {
            CallableStatement cal = cnn.prepareCall(this.SQL_SELECT, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = cal.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String khu = rs.getString(2);
                String dot = rs.getString(3);
                String danhBo = rs.getString(4);
                String cuLy = rs.getString(5);
                String hopDong = rs.getString(6);
                String tenKhachHang = rs.getString(7);
                String soNha = rs.getString(8);
                String duong = rs.getString(9);
                String giaBieu = rs.getString(10);
                String dinhMuc = rs.getString(11);
                String ky = rs.getString(12);
                String nam = rs.getString(13);
                String code = rs.getString(14);
                String codeFU = rs.getString(15);
                String chiSoCu = rs.getString(16);
                String chiSoMoi = rs.getString(17);
                String quan = rs.getString(18);
                String phuong = rs.getString(19);
                String maLoTrinh = rs.getString(20);
                HoaDon course = new HoaDon(id, khu, dot, danhBo, cuLy, hopDong, tenKhachHang, soNha, duong, giaBieu, dinhMuc, ky, nam, code, codeFU, chiSoCu, chiSoMoi, quan, phuong, maLoTrinh);
                result.add(course);
            }
            rs.close();
            cal.close();
            cnn.close();
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return result;
    }

    public List<String> getAllMaLoTrinh() throws SQLException {
        List<String> result = new ArrayList<String>();
        Connection cnn = this.condb.getConnect();
        try {
            Statement statement = cnn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT DISTINCT MLT FROM " + this.TABLE_NAME);
            while (rs.next()) {
                String maLoTrinh = rs.getString(1);
                result.add(maLoTrinh);
            }
            rs.close();
            statement.close();
            cnn.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return result;
    }

    public List<HoaDon> getByMaLoTrinh(String maLoTrinh) {
        List<HoaDon> result = new ArrayList<HoaDon>();
        Connection cnn = this.condb.getConnect();
        try {
            Statement statement = cnn.createStatement();
            ResultSet rs = statement.executeQuery(this.SQL_SELECT + " WHERE MLT = '" + maLoTrinh + "'");
            while (rs.next()) {
                int id = rs.getInt(1);
                String khu = rs.getString(2);
                String dot = rs.getString(3);
                String danhBo = rs.getString(4);
                String cuLy = rs.getString(5);
                String hopDong = rs.getString(6);
                String tenKhachHang = rs.getString(7);
                String soNha = rs.getString(8);
                String duong = rs.getString(9);
                String giaBieu = rs.getString(10);
                String dinhMuc = rs.getString(11);
                String ky = rs.getString(12);
                String nam = rs.getString(13);
                String code = rs.getString(14);
                String codeFU = rs.getString(15);
                String chiSoCu = rs.getString(16);
                String chiSoMoi = rs.getString(17);
                String quan = rs.getString(18);
                String phuong = rs.getString(19);

                HoaDon course = new HoaDon(id, khu, dot, danhBo, cuLy, hopDong, tenKhachHang, soNha, duong, giaBieu, dinhMuc, ky, nam, code, codeFU, chiSoCu, chiSoMoi, quan, phuong, maLoTrinh);
                result.add(course);
            }
            rs.close();
            cnn.close();
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return result;
    }
    public HoaDon getByDanhBo(String danhBo) {
        HoaDon hoaDon = null;
        Connection cnn = this.condb.getConnect();
        try {
            Statement statement = cnn.createStatement();
            ResultSet rs = statement.executeQuery(this.SQL_SELECT + " WHERE DANHBO = '" + danhBo + "'");
            if (rs.next()) {
                int id = rs.getInt(1);
                String khu = rs.getString(2);
                String dot = rs.getString(3);
                String cuLy = rs.getString(5);
                String hopDong = rs.getString(6);
                String tenKhachHang = rs.getString(7);
                String soNha = rs.getString(8);
                String duong = rs.getString(9);
                String giaBieu = rs.getString(10);
                String dinhMuc = rs.getString(11);
                String ky = rs.getString(12);
                String nam = rs.getString(13);
                String code = rs.getString(14);
                String codeFU = rs.getString(15);
                String chiSoCu = rs.getString(16);
                String chiSoMoi = rs.getString(17);
                String quan = rs.getString(18);
                String phuong = rs.getString(19);
                String maLoTrinh = rs.getString(20);
                hoaDon = new HoaDon(id, khu, dot, danhBo, cuLy, hopDong, tenKhachHang, soNha, duong, giaBieu, dinhMuc, ky, nam, code, codeFU, chiSoCu, chiSoMoi, quan, phuong, maLoTrinh);

            }
            rs.close();
            cnn.close();
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return hoaDon;
    }
    public int getNum_DanhBo_ByMLT(String maLoTrinh) {
        int result = 0;
        Connection cnn = this.condb.getConnect();
        try {
            Statement statement = cnn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT COUNT(DANHBO) FROM " + TABLE_NAME+" WHERE MLT = '" + maLoTrinh + "'");
            if (rs.next()) {
                result = rs.getInt(1);
            }
            rs.close();
            statement.close();
            cnn.close();
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return result;
    }
    public List<String> get_DanhBo_ByMLT(String maLoTrinh) {
        List<String> result = new ArrayList<String>();
        Connection cnn = this.condb.getConnect();
        try {
            Statement statement = cnn.createStatement();
            ResultSet rs = statement.executeQuery(this.SQL_SELECT_DANHBO + " WHERE MLT = '" + maLoTrinh + "'");
            while (rs.next()) {
                result.add(rs.getString("DANHBO"));
            }
            rs.close();
            statement.close();
            cnn.close();
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        HoaDonDB cdb = new HoaDonDB();
        List<String> result = null;
        try {
            result = cdb.get_DanhBo_ByMLT("00005");
            for(String s: result)
                System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
//		List<HoaDon> results = cdb.getByMaLoTrinh("22800");
//		for (HoaDon c : results) {
//			System.out.println(c);
//		}

    }

}
