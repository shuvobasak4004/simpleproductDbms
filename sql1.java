import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class sql1 {
    private JPanel main;
    private JTextField txtName;
    private JTextField txtPrice;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTextField txtid;
    private JButton searchButton;
    private JTextField txtQty;
    private JTable table1;
    private  JScrollPane table_1;

    public static void main(String[] args) {//main
        JFrame frame = new JFrame("Shuvo-04");
        frame.setContentPane(new sql1().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;

    public sql1() {
        Connect();
        table_load();

        saveButton.addActionListener(new ActionListener() {//save
            @Override
            public void actionPerformed(ActionEvent e) {

         String name ,price ,qty,pid;
         name = txtName.getText();
         price = txtPrice.getText();
         qty = txtQty.getText();
         pid = txtid.getText();
         try{
             pst = con.prepareStatement("insert into products(pid,pname , price , qty)values(?,?,?,?)");
            pst.setString(1,pid);
            pst.setString(2,name);
            pst.setString(3,price);
            pst.setString(4,qty);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"jkjk");
            txtid.setText("");
            txtName.setText("");
            txtQty.setText("");
            txtPrice.setText("");
            txtName.requestFocus();



         }
         catch (SQLException e1){
             e1.printStackTrace();
         }




            }
        });
        //save b end
        //search
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String pid = txtid.getText();
                    pst = con.prepareStatement("select pname ,price , qty from products where pid = ?");

                    pst.setString(1,pid);
                    ResultSet rs = pst.executeQuery();
                    if(rs.next()==true){
                        String name = rs.getString(1);
                        String price = rs.getString(2);
                        String qty = rs.getString(3);
                        txtName.setText(name);
                        txtPrice.setText(price);
                        txtQty.setText(qty);
                    }
                    else{
                        txtName.setText("");
                        txtPrice.setText("");
                        txtQty.setText("");
                        JOptionPane.showMessageDialog(null,"invalid");

                    }
                }
                catch (SQLException ex){
                    ex.printStackTrace();

                }

            }
        });
        //end
        //update
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name ,price ,qty,pid;
                name = txtName.getText();
                price = txtPrice.getText();
                qty = txtQty.getText();
                pid = txtid.getText();
                try {
                    pst = con.prepareStatement("update products set pname = ? ,price = ? , qty = ?where pid = ? ");
                    pst.setString(1,name);
                    pst.setString(2,price);
                    pst.setString(3,qty);
                    pst.setString(4,pid);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"update");

                    txtName.setText("");
                    txtQty.setText("");
                    txtPrice.setText("");
                    txtName.requestFocus();
                    txtid.setText("");
                }
                catch (SQLException e1){
                    e1.printStackTrace();
                }

            }
        });
        //end
        //delete
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String bid;
               bid = txtid.getText();
                try {
                    pst = con.prepareStatement("delete from products where pid = ? ");
                    pst.setString(1,bid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"delete");

                    txtName.setText("");
                    txtQty.setText("");
                    txtPrice.setText("");
                    txtName.requestFocus();
                    txtid.setText("");
                }
                catch (SQLException e1){
                    e1.printStackTrace();
                }

            }
        });
    }

void table_load(){

        try {
            pst = con.prepareStatement("select * from products");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
            table1.setAutoResizeMode(table1.AUTO_RESIZE_OFF);

        }
        catch (SQLException e1){
            e1.printStackTrace();
        }


}


    public void Connect()
    {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/gbproducts","root","");
            System.out.println("Success");

        }
        catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
