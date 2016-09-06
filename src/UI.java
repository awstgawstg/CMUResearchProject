import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import java.util.ArrayList;

public class UI extends Frame implements ActionListener{
      // Declare a Label component
    private Button addNewNode;
    private Button addNewNodeSub;
    private Button inputEvent;
    private TextField col1;
    private TextField col2;
    private TextField col3;
    private TextField nodeName;

    databaseConfig db= new databaseConfig();

      // Declare a Button component

    public UI() {
        setLayout(new FlowLayout());

        addNewNode  = new Button("Add New Node");   // construct the Button component
        add(addNewNode);                    // "super" Frame adds Button

        addNewNode.addActionListener(this);


        setTitle("AWT Counter");  // "super" Frame sets its title
        setSize(1000, 800);        // "super" Frame sets its initial window size
        setVisible(true);         // "super" Frame shows

    }



    public  void addNewNodePage(){
        setLayout(new FlowLayout());
        Label Nlabel=new Label("Name:");
        add(Nlabel);
        nodeName= new TextField(10);
        Label Clabel=new Label("Col:");
        col1= new TextField(10);
        col2= new TextField(10);
        col3= new TextField(10);
        add(nodeName);
        add(Clabel);

        add(col1);
        add(col2);
        add(col3);

        addNewNodeSub= new Button("Submit");
        add(addNewNodeSub);
        addNewNodeSub.addActionListener(this);

        setVisible(true);
    }

    public static void main(String[] args) {
        UI newmain= new UI();
        //databaseConfig db= new databaseConfig();
        //db.CreateNodeData("hehehe",null);

    }


    @Override
    public void actionPerformed(ActionEvent evt) {
     if(evt.getSource()==addNewNode){
         addNewNodePage();
     }
        if(evt.getSource()==addNewNodeSub){
            try{
                ArrayList<String> myList = new ArrayList<String>();
                if(col1.getText().length()>0)
                    myList.add(col1.getText());
                if(col2.getText().length()>0)
                    myList.add(col2.getText());
                if(col3.getText().length()>0)
                    myList.add(col3.getText());
                String listString="";
                for (String s : myList)
                {
                    listString += s + "\t";
                }

                System.out.println(listString);
                db.CreateNodeData(nodeName.getText(), myList);

            }
            catch(Exception e){
                System.out.print(e);
            }

        }

    }




    public static class databaseConfig{
        public Connection con=connectDB();




        public void CreateNodeData(String name, ArrayList<String> data){
            //System.out.print(data);
            String query = "insert into \"Nodes\" (\"NameKey\") values ('"+name+"')";
            String createTable="CREATE TABLE \""+name+"\"(";
            int length=data.size()-1;
            int i=0;
            for (String s : data)
            {
                if(i==0) {
                    createTable = createTable + s + " Text";
                }
                if(i==length){
                    createTable = createTable + "," + s + " Text)";
                }
                if(i!=0&&i!=length)
                    createTable = createTable + "," + s + " Text";
                i++;


            }
            System.out.print(createTable);
            try {
                executeNonSelectQuery(query);
                executeNonSelectQuery(createTable);
            }
            catch(Exception e){
                System.out.print(e);
            }

        }

       public void CreateRelation(String firstTable,String secondTable){

       }


        public ResultSet executeQuery(String query)  {

            try {
                Statement stmt=con.createStatement();
                return stmt.executeQuery(query);
            } catch (SQLException e) {
                e.printStackTrace();
                commit();
                return null;
            }
        }


        public void executeNonSelectQuery(String query){
            try {
                // System.out.println(query);
                Statement stmt=con.createStatement();
                stmt.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }




        public void commit() {
            try {
                con.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }




        public Connection connectDB(){

            System.out.println("-------- PostgreSQL "
                    + "JDBC Connection Testing ------------");

            try {

                Class.forName("org.postgresql.Driver");

            } catch (ClassNotFoundException e) {

                System.out.println("Where is your PostgreSQL JDBC Driver? "
                        + "Include in your library path!");
                e.printStackTrace();
                return null;

            }

            System.out.println("PostgreSQL JDBC Driver Registered!");

            Connection connection = null;

            try {

                connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/ResearchGraph", "postgres",
                        "postgres");

            } catch (SQLException e) {

                System.out.println("Connection Failed! Check output console");
                e.printStackTrace();
                return null;

            }

            if (connection != null) {
                return connection;
            } else {
                System.out.println("Failed to make connection!");
                return null;
            }

        }

    }



}
