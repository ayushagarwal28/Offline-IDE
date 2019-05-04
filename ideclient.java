
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.logging.*;

public class ideclient extends JFrame 
{
     
    public ideclient() 
    {
        init();
    }
    private void buttonActionPerformed(ActionEvent evt) throws IOException, ClassNotFoundException                         			//action of compile button
    {
        
        
        if( !( java_button.isSelected() | c_button.isSelected() |cpp_button.isSelected() ) | (code_editor.getText().equals("")))	//invalid inputs
        {
            output_window.setText("Error:Input Error");
        }			
        else                                                                                                  						//Actual Working Portion
        {
            String ipadd=ip_f.getText();																							//Ip add and port of server
            String portadd=port_f.getText();
            
            output_window.setText("Waiting For Server");

            
            File fi = new File("code.txt");																							// *******save code in code.txt
            FileWriter wr = new FileWriter(fi, false); 
            BufferedWriter w = new BufferedWriter(wr);
            w.write(code_editor.getText());
            w.close();
            
            
            fi = new File("input.txt");																								//save inputs in txt
            wr = new FileWriter(fi, false); 
            w = new BufferedWriter(wr);
            w.write(input_editor.getText());
            w.close();
        
            if(fi.length() == 0)																									//check for emptyfile
            {
                FileWriter fileWriter = new FileWriter(fi);
                fileWriter.write("Error:No_input_provided");
                fileWriter.close();
            }

            String ch = "";																											//Language Choice
            if(java_button.isSelected())
                ch = "java";
            else if(c_button.isSelected())
                ch = "c_language";
            else
                ch = "cpp";


            
            client_socket cs=new client_socket(ch,ipadd,portadd);																	// *******Transfer to server


            String stemp = "", str = ""; 																							// *******Display Output
            FileReader fr = new FileReader("output.txt");
            BufferedReader br = new BufferedReader(fr);
            str = br.readLine();
            while ((stemp = br.readLine()) != null)
            {
                  str = str + "\n" + stemp;
            }
            output_window.setText(str);   

            new File("output.txt").delete(); 																						//Delete Temporary files
            new File("input.txt").delete();
            new File("code.txt").delete();
        }
        
    }                                      

    JButton button;                                         																		//Declaring UI Components
    JEditorPane code_editor;
    JLabel code_title;
    JEditorPane input_editor;
    JLabel input_title;
    JRadioButton c_button;
    JRadioButton cpp_button;
    JRadioButton java_button;
    JScrollPane jScrollPane1;
    JScrollPane jScrollPane2;
    JScrollPane jScrollPane4;
    JLabel lang_head;
    JLabel output_head;
    JTextPane output_window;
    JLabel title;
    ButtonGroup bg; 
    
    JTextField ip_f;
    JTextField port_f;
    JLabel iplabel;
    JLabel portlabel;

    private void init() 
    {

        title = new JLabel("Welcome To Online IDE");                               									//Initilization of Components
        code_title = new JLabel("Code Editor");
        input_title = new JLabel("Input Parameters");
        output_head = new JLabel("Output Window");
        lang_head = new JLabel("Language");
        jScrollPane1 = new JScrollPane();
        jScrollPane2 = new JScrollPane();
        jScrollPane4 = new JScrollPane();
        code_editor = new JEditorPane();
        input_editor = new JEditorPane();
        c_button = new JRadioButton("C");
        cpp_button = new JRadioButton("C++");
        java_button = new JRadioButton("Java");
        button = new JButton("Compile And Run");
        output_window = new JTextPane();

        ip_f=new JTextField("127.0.0.1");																			// UI for server config
        port_f=new JTextField("50102");
        iplabel=new JLabel("Server Configuration :  IP");
        portlabel=new JLabel("Port");
        
        bg=new ButtonGroup();																						//Grouping three buttons
        bg.add(c_button);
        bg.add(cpp_button);
        bg.add(java_button);
        
        java_button.addActionListener( ae ->																		//Default text for editor
        {
                code_editor.setText("public class code \n{\n	public static void main(String[] args)\n 	{\n 	}\n}");
        });
        c_button.addActionListener( ae ->
        {
                code_editor.setText("#include<stdio.h>\nint main()\n{\n\n}");
        });
        cpp_button.addActionListener( ae ->
        {
                code_editor.setText("#include<iostream>\nusing namespace std;\nint main()\n{\n\n}");
        });
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);            										//Frame behaviours
        setResizable(false);

        output_window.setEditable(false);

        code_editor.setFont(new Font("Segoe UI", 0, 15));															//Font of each component
        input_editor.setFont(new Font("Segoe UI", 0, 15));
        output_window.setFont(new Font("Segoe UI", 0, 15));

        title.setFont(new Font("Segoe UI", 1, 36));                            
        code_title.setFont(new Font("Segoe UI", 1, 18));
        input_title.setFont(new Font("Segoe UI", 1, 18));
        button.setFont(new Font("Segoe UI", 3, 24)); 
        output_head.setFont(new java.awt.Font("Segoe UI", 1, 18)); 
        lang_head.setFont(new java.awt.Font("Segoe UI", 1, 18)); 
        
        
        
        jScrollPane1.setViewportView(code_editor);                         											//Adding scrollers for editors
        jScrollPane2.setViewportView(input_editor);
        jScrollPane4.setViewportView(output_window);
        

        

        button.addActionListener(new ActionListener()                       										//Compile Button action
        {
            public void actionPerformed(ActionEvent evt) 
            {
                try {
                    buttonActionPerformed(evt);
                } catch (IOException ex) {
                    Logger.getLogger(ideclient.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ideclient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());             										//UI Configuration
        
        // The code below is generated by Netbeans JFrame Forms Designer
        getContentPane().setLayout(layout);													
        
        layout.setHorizontalGroup(                                          										//Horizontal Spacing
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(title, GroupLayout.PREFERRED_SIZE, 695, GroupLayout.PREFERRED_SIZE)
                .addGap(212, 212, 212))
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(iplabel, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                .addGap(30,30,30)
                .addComponent(ip_f, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                .addGap(30,30,30)
                .addComponent(portlabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                
                .addComponent(port_f, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                .addGap(30,30,30)
                )
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 960, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(code_title)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(input_title)
                        .addGap(181, 181, 181))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(lang_head)
                            .addComponent(c_button)
                            .addComponent(button, GroupLayout.PREFERRED_SIZE, 285, GroupLayout.PREFERRED_SIZE)
                            .addComponent(java_button)
                            .addComponent(cpp_button))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(output_head)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane4))
                        .addContainerGap())))
        );

        layout.setVerticalGroup(                                                   									 //Vertical Spacing
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(iplabel)
                    .addComponent(ip_f)
                    .addComponent(portlabel)
                    .addComponent(port_f))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(code_title)
                    .addComponent(input_title))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lang_head)
                    .addComponent(output_head))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(c_button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cpp_button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(java_button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(button, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }
 
    public static void main(String args[])                                      								//Main function
    {
        //EventQueue.invokeLater	:	Causes runnable to have its run method called in the dispatch thread of the system EventQueue.//
        EventQueue.invokeLater(  () -> 																			
        {
            new ideclient().setVisible(true);
        });
    }
    
}