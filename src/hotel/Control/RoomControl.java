/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.Control;

import hotel.Model.Room;
import hotel.View.AddRoomFrm;
import hotel.View.ModifyRoomFrm;
import hotel.View.MainInterface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author er1nzz
 */
public class RoomControl {
    
    private Room room;
    
    private MainInterface mi;
    
    private AddRoomFrm arf;
    private ModifyRoomFrm mdf;
    
    private Connection connect(){
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
    
    public RoomControl(){
        
        
        mi = new MainInterface();
        mi.setVisible(true);
        mi.addInterfaceListener(new AddControlInterfaceListener());
        mi.modifyInterfaceListener(new ModifyControlInterfaceListener());
        
        arf = new AddRoomFrm();
        arf.addSummitListener(new AddRoomListener());
        
        mdf = new ModifyRoomFrm();
        mdf.SearchListener(new SearchRoomListener());
        mdf.ShowAllListener(new ShowAllListener());
        mdf.DeleteListener(new DeleteRoomListener());
        mdf.UpdateListener(new UpdateRoomListener());
    }
    
    //execute 
    
    public void addRoom(Room room){
        String sql = "INSERT INTO tblRoom(id, name, type, displayPrice, description) VALUES(?,?,?,?,?)";
        Connection con=connect();
        if (con!=null){
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
    
    public ArrayList searchRoom(String target){
        ArrayList<Room> ls = new ArrayList();
        String sql = "SELECT * FROM `tblroom` WHERE name=\""+target+"\"";
        Connection con = connect();
        if (con != null) {
            try {
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        Room g = new Room(
                                rs.getString("id"),
                                rs.getString("name"),
                                rs.getString("type"),
                                rs.getString("displayPrice"),
                                rs.getString("description")

                        );

                        ls.add(g);
                    }
                    con.close();
                }

            } catch (Exception e) {
            }
        }
        return ls;
    }
    
    public void deleteRoom(String target){
        String sql = "DELETE FROM `tblroom` WHERE name=\"" + target + "\"";
        Connection con = connect();
        if (con != null) {
            try {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.executeUpdate();
                con.close();
            } catch (Exception e) {
            }
        }
        
    }
    
    public ArrayList showAllRoom() {
        ArrayList<Room> ls = new ArrayList();
        String sql = "SELECT * FROM `tblroom`";
        Connection con = connect();
        if (con != null) {
            try {
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Room g = new Room(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getString("type"),
                            rs.getString("displayPrice"),
                            rs.getString("description")
                    );

                    ls.add(g);
                }    
                con.close();

            } catch (Exception e) {
            }
        }
        
        return ls;
    }
    
    //sub interface control
    
    class AddRoomListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                room = arf.getRoom();
                addRoom(room);
                arf.showMessage("Thêm thành công!");
            } catch (Exception x) {
                x.printStackTrace();
            }
        }
        
    }
    
    class SearchRoomListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            
            try {
                mdf.clearTbl();
                String target = mdf.getTarget();
                ArrayList<Room> ls = searchRoom(target);
                if (ls.isEmpty()) {
                    mdf.showMessage("Không tìm thấy");
                }
                else{
                    int i;
                    for(i=0;i<ls.size();i++){
                        mdf.addRowModel(new Object[]{ls.get(i).getId(),ls.get(i).getName(),ls.get(i).getType(),ls.get(i).getDisplayPrice(),ls.get(i).getDescription()});
                    }
                }
            } catch (Exception ex) {
            }
        }
        
    }
    
    class UpdateRoomListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Room origin = new Room(mdf.getInfo(mdf.getCurrentSelected(), 0), mdf.getInfo(mdf.getCurrentSelected(), 1), mdf.getInfo(mdf.getCurrentSelected(), 2), mdf.getInfo(mdf.getCurrentSelected(), 3), mdf.getInfo(mdf.getCurrentSelected(), 4));
            
            UpdateControl ec = new UpdateControl(origin);
            mdf.clearTbl();
        }

    }
    
    class DeleteRoomListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String target = mdf.getInfo(mdf.getCurrentSelected(), 1);
                
                deleteRoom(target);
                mdf.showMessage("Đã xoá thành công!");
                mdf.clearTbl();
            } catch (Exception ex) {
            }
            
        }

    }
    
    class ShowAllListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                mdf.clearTbl();
                String target = mdf.getTarget();
                ArrayList<Room> ls = showAllRoom();
                int i;
                for (i = 0; i < ls.size(); i++) {
                    mdf.addRowModel(new Object[]{ls.get(i).getId(), ls.get(i).getName(), ls.get(i).getType(), ls.get(i).getDisplayPrice(), ls.get(i).getDescription()});
                }
                    
                
            } catch (Exception ex) {
            }
        }
        

    }

    //main interface control
    
    class AddControlInterfaceListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            arf.setVisible(true);
        }

    }
    
    class ModifyControlInterfaceListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            mdf.setVisible(true);
        }

    }
}
