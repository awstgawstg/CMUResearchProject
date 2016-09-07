import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import javax.swing.*;


public class UI extends Frame implements ActionListener,ItemListener{
      // Declare a Label component
    private JButton addNewNodeBtn;
    private JButton addNewNodeSubBtn;
    private JButton insertDataBtn;
    private JButton inputEvent;
    private  JPanel buttonPane;
    private TextField newNodeCol1;
    private TextField newNodeCol2;
    private TextField newNodeCol3;
    private TextField insertNodeCol1;
    private TextField insertNodeCol2;
    private TextField insertNodeCol3;
    private TextField nodeName;
    private Container that;
    private JPanel cards;
    JFrame frame;

    

    databaseConfig db= new databaseConfig();

      // Declare a Button component

    public UI() {
        setLayout(new FlowLayout());
        frame = new JFrame("CardLayoutDemo");


        addNewNodeBtn  = new JButton("Add New Node");   // construct the Button component
        insertDataBtn  = new JButton("insertDataBtn");   // construct the Button component
        setLayout(new GridBagLayout());

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

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(100, 100, 100, 100);
        add(buttonPane, gbc);



        addNewNodeBtn.addActionListener(this);
        insertDataBtn.addActionListener(this);


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


    public  void insertDataPage(){
        final ArrayList<String> Tablelist = db.getTables();
        final String[] description = Tablelist.toArray(new String[Tablelist.size()]);
        final JComboBox tableBox = new JComboBox();
        for (int i = 0; i < description.length; i++)
            tableBox.addItem(description[i]);
        add(tableBox);
        that=this;
        tableBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        setVisible(true);







    }



    public void addComponentToPane(Container pane) {
        String BUTTONPANEL = "Card with JButtons";
        String TEXTPANEL = "Card with JTextField";
        //Put the JComboBox in a JPanel to get a nicer look.
        JPanel comboBoxPane = new JPanel(); //use FlowLayout
        String comboBoxItems[] = { BUTTONPANEL, TEXTPANEL };
        JComboBox cb = new JComboBox(comboBoxItems);
        cb.setEditable(false);
        comboBoxPane.add(cb);

        //Create the "cards".
        JPanel card1 = new JPanel();
        card1.add(new JButton("Button 1"));
        card1.add(new JButton("Button 2"));
        card1.add(new JButton("Button 3"));

        JPanel card2 = new JPanel();
        card2.add(new JTextField("TextField", 20));

        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(card1, BUTTONPANEL);
        cards.add(card2, TEXTPANEL);
        //add(comboBoxPane);
        setVisible(true);

    }

    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)evt.getItem());
    }







    public static void main(String[] args) {
        UI newmain= new UI();
        //databaseConfig db= new databaseConfig();
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


    }








}
