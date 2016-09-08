import jdk.nashorn.internal.runtime.ECMAException;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import javax.swing.*;


public class UI extends Frame implements ActionListener{
      // Declare a Label component
    private JButton addNewNodeBtn;
    private JButton addNewNodeSubBtn;
    private JButton insertDataBtn;
    private JButton insertSubBtn;
    private JButton insertRelationBtn;
    private JButton insertRelationSubBtn;
    private  JPanel buttonPane;
    private TextField newNodeCol1;
    private TextField newNodeCol2;
    private TextField newNodeCol3;
    private TextField insertNodeCol1;
    private TextField insertNodeCol2;
    private TextField insertNodeCol3;
    private TextField insertRelationCol1;
    private TextField insertRelationCol2;
    private TextField insertRelationCol3;
    private TextField nodeName;
    private Container that;
    private JPanel cards;
    private JFrame frame;
    private JComboBox tableBox = new JComboBox();
    FlowLayout experimentLayout = new FlowLayout();
    

    databaseConfig db= new databaseConfig();

      // Declare a Button component

    public UI() {

       setLayout(new FlowLayout());


        
        addNewNodeBtn  = new JButton("Add New Node");   // construct the Button component
        insertDataBtn  = new JButton("insert Data");   // construct the Button component
        //setLayout(new GridBagLayout());
        insertRelationBtn = new JButton("Insert Relation");

        buttonPane = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        buttonPane.add(addNewNodeBtn, gbc);
        gbc.gridy++;
        gbc.insets = new Insets(50, 0, 0, 0);
        buttonPane.add(insertDataBtn, gbc);
        gbc.gridy++;
        gbc.insets = new Insets(50, 0, 0, 0);
        buttonPane.add(insertRelationBtn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(100, 100, 100, 100);
        add(buttonPane);

        addNewNodeBtn.addActionListener(this);
        insertDataBtn.addActionListener(this);
        insertRelationBtn.addActionListener(this);



        setTitle("AWT Counter");  // "super" Frame sets its title
        setSize(1000, 800);        // "super" Frame sets its initial window size
        setVisible(true);         // "super" Frame shows

    }



    public  void addNewNodePage(){
        //setLayout(new FlowLayout());
        Label Nlabel=new Label("Name:");
        add(Nlabel);
        nodeName= new TextField(10);
        Label Clabel=new Label("Col:");
        newNodeCol1= new TextField(10);
        newNodeCol2= new TextField(10);
        newNodeCol3= new TextField(10);
        add(nodeName);
        add(Clabel);

        add(newNodeCol1);
        add(newNodeCol2);
        add(newNodeCol3);

        addNewNodeSubBtn= new JButton("Submit");
        add(addNewNodeSubBtn);
        addNewNodeSubBtn.addActionListener(this);

        setVisible(true);
    }



    public  void insertRelationPage(){
        insertRelationCol1 =  new TextField(10);
        insertRelationCol2 =  new TextField(10);

        insertRelationCol3 =  new TextField(10);
        insertRelationSubBtn = new JButton("Submit");
        add(insertRelationCol1);
        add(insertRelationCol2);
        add(insertRelationCol3);
        add(insertRelationSubBtn);

        setVisible(true);



    }




    public  void insertDataPage(){
        final ArrayList<String> Tablelist = db.getTables();
        final String[] description = Tablelist.toArray(new String[Tablelist.size()]);

        for (int i = 0; i < description.length; i++)
            tableBox.addItem(description[i]);
        add(tableBox);
        that=this;
        insertNodeCol1= new TextField(20);
        insertNodeCol2= new TextField(20);
        insertNodeCol3= new TextField(20);
        insertSubBtn = new JButton("Submit");
        insertSubBtn.addActionListener(this);
        add(insertNodeCol1);
        add(insertNodeCol2);
        add(insertNodeCol3);
        add(insertSubBtn);
        setVisible(true);

    }




    public static void main(String[] args) {
        UI newmain= new UI();
        //databaseConfig db= new databaseConfig();
        //db.createRelation("db1","db2","hehehe");
        //db.CreateNodeData("hehehe",null);

    }


    @Override
    public void actionPerformed(ActionEvent evt) {
     if(evt.getSource()==addNewNodeBtn){
         addNewNodePage();
     }
        if(evt.getSource()==addNewNodeSubBtn){
            try{
                ArrayList<String> myList = new ArrayList<String>();
                if(newNodeCol1.getText().length()>0)
                    myList.add(newNodeCol1.getText());
                if(newNodeCol2.getText().length()>0)
                    myList.add(newNodeCol2.getText());
                if(newNodeCol3.getText().length()>0)
                    myList.add(newNodeCol3.getText());
                String listString="";
                for (String s : myList)
                {
                    listString += s + "\t";
                }

                System.out.println(listString);
                db.createNodeData(nodeName.getText(), myList);

            }
            catch(Exception e){
                System.out.print(e);
            }

        }
        if(evt.getSource()==insertDataBtn){
            try{
                insertDataPage();
                System.out.print("insertDataBtn Clicked");

            }
            catch (Exception e){
                System.out.print(e);

            }
        }

        if(evt.getSource()==insertSubBtn){
            try{
                ArrayList<String> myList = new ArrayList<String>();
                if(insertNodeCol1.getText().length()>0)
                    myList.add(insertNodeCol1.getText());
                if(insertNodeCol2.getText().length()>0)
                    myList.add(insertNodeCol2.getText());
                if(insertNodeCol3.getText().length()>0)
                    myList.add(insertNodeCol3.getText());
                db.insertData(tableBox.getSelectedItem().toString(),myList);
            }
            catch(Exception e){
                System.out.print(e);
            }
        }


        if(evt.getSource()==insertRelationBtn){
            try{
                insertRelationPage();
            }
            catch(Exception e){
                System.out.print(e);

            }
        }

        if(evt.getSource()==insertRelationSubBtn){
            try{
               db.CreateRelation(insertRelationCol1.getText(),insertRelationCol2.getText(),insertRelationCol3.getText());
            }
            catch(Exception e){

            }
        }


    }








}
