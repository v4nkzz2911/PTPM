/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.Control;

import hotel.Model.Room;
import hotel.View.UpdateRoomInfoFrm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 *
 * @author er1nzz
 */
public class UpdateControl {
    private Room room;
    private Room origin;
    
    private UpdateRoomInfoFrm urif;

    private Connection connect() {
        Connection con;
        String dbURL = "jdbc:mysql://localhost:3306/hotel";
        String dbClass = "com.mysql.jdbc.Driver";
        try {
            Class.forName(dbClass);
            con = DriverManager.getConnection(dbURL, "root", "");
            return con;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public UpdateControl(Room origin) {
        this.origin = origin;
        urif = new UpdateRoomInfoFrm();
        urif.setupInfo(origin);
        urif.setVisible(true);
        urif.updateSummitListener(new UpdateInfoRoomListener());
    }
    
    public void updateRoom(Room room, Room origin) {
        String sql = "UPDATE `tblRoom`"
                +       "SET `id`=?,`name`=?,`type`=?,`displayPrice`=?,`description`=?"
                +       "WHERE name=\"" + origin.getName() + "\"";
        Connection con = connect();
        if (con != null) {
            try {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, room.getId());
                ps.setString(2, room.getName());
                ps.setString(3, room.getType());
                ps.setString(4, room.getDisplayPrice());
                ps.setString(5, room.getDescription());
                ps.executeUpdate();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    class UpdateInfoRoomListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                
                room = urif.getRoom();
                updateRoom(room, origin);
                urif.showMessage("Cập nhật thành công!");
                urif.setVisible(false);
            } catch (Exception x) {
                x.printStackTrace();
            }
        }

    }
    
}
