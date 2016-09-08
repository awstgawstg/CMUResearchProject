import java.sql.*;
import java.util.ArrayList;

/**
 * Created by dingzhang on 9/6/16.
 */
public class databaseConfig {


        public Connection con=connectDB();




    public void insertData(String name, ArrayList<String> data){
       String insertQuery="insert into \""+name+"\" values ('";
        int i=0;
        int length=data.size()-1;
        for (String s : data)
        {
            if(i==0) {
                insertQuery = insertQuery + s + "',";
            }
            if(i==length){
                insertQuery = insertQuery  + s + "')";
            }
            if(i!=0&&i!=length)
                insertQuery = insertQuery + "'" + s + "','";
            i++;


        }
        System.out.print(insertQuery);
        try {
            executeNonSelectQuery(insertQuery);
        }
        catch(Exception e){
            System.out.print(e);
        }


    }


    public void createRelation(String db1, String db2, String Relation){
        String findTableKey="Select IndexKey from \'Nodes\' where NameKey = '"+db1+"' or NameKey ='"+db2+"'";
        ArrayList<String> tableList = new ArrayList<String>();
        System.out.print(findTableKey);
        try{
            ResultSet rs = executeQuery(findTableKey);
            while (rs.next()) {
                tableList.add(rs.getString(1));
            }
            System.out.print(tableList);


        }
        catch (Exception e){
            System.out.print(e);
        }
        String insertQuery="insert into \"Relations\" values ('"+db1+"','"+db2+"','"+Relation+"')";


    }





    //Create Node table
        public void createNodeData(String name, ArrayList<String> data){
            //System.out.print(data);
            String insertTable="insert into \"Nodes\" (\"NameKey\") values('"+name+"')";
            System.out.print(insertTable);
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
                executeNonSelectQuery(insertTable);
                executeNonSelectQuery(createTable);
            }
            catch(Exception e){
                System.out.print(e);
            }

        }


    public ArrayList<String> getTables(){
        ArrayList<String> tableList = new ArrayList<String>();
        String gettablequery="SELECT \"NameKey\" from \"Nodes\"";
        System.out.print(gettablequery);
        try{
            ResultSet rs = executeQuery(gettablequery);
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                tableList.add(rs.getString(1));
            }
            return tableList;

        }
        catch (Exception e){
            System.out.print(e);
        }
        return null;
    }




    public ArrayList<String> getTablesAndKey(){
        ArrayList<String> tableList = new ArrayList<String>();
        String gettablequery="SELECT * from \"Nodes\"";
        System.out.print(gettablequery);
        try{
            ResultSet rs = executeQuery(gettablequery);
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                tableList.add(rs.getString(1)+"|||"+Integer.toString(rs.getInt(2)));
            }
            return tableList;

        }
        catch (Exception e){
            System.out.print(e);
        }
        return null;
    }






        public void CreateRelation(String firstTable,String secondTable, String Relation){


        }


        //execute select query
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

        //execute Non Select Query like insert delete
        public void executeNonSelectQuery(String query){
            try {
                // System.out.println(query);
                Statement stmt=con.createStatement();
                stmt.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }



        //commit query
        public void commit() {
            try {
                con.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }



        //basic connect to database
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

