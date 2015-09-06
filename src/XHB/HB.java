package XHB;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.UIManager;


public class HB extends JFrame //���࣬��չ��JFrame�࣬��������������
 {
  /**
	 * 
	 */
	private static final long serialVersionUID = -8681308449734641325L;
private ObjectInputStream  input;
  private ObjectOutputStream output; //����������������������úͱ���ͼ���ļ�
  private JButton choices[];         //��ť���飬����������ƵĹ��ܰ�ť
private String names[]={
          "New",
          "Open",
          "Save",    //�������ǻ���������ť������"�½�"��"��"��"����"
    /*���������ǵĻ�ͼ�������еĻ����ļ�����ͼ��Ԫ��ť*/
          "Pencil",		
          "Line",		
          "Rect",		
          "FRect",		
          "Oval",		
          "FOval",		
          "Circle",		
          "FCircle",	
          "RoundRect",	
          "FrRect",		
          "Rubber",		
          "Color",		
          "Stroke",		
          "Word"		
          };

  private String styleNames[]={
		  "����" ,
		  "����" ,
		  "���Ĳ���" ,
		  "����_GB2312" ,
		  "�����п�" ,
          "��������" ,
          "Times New Roman" ,
          "Serif" ,
          "Monospaced" ,
          "SonsSerif" ,
          "Garamond"
          };        
  private Icon items[];
  private String tipText[]={
                  //����������ƶ�����Ӧ��ť������ͣ��ʱ��������ʾ˵����
                "�½�һ���ļ�",
                "��һ���ļ�",
                "���浱ǰ�ļ�",
                "��������",
                "����ֱ��",
                "���ƿ��ľ���",
                "����ʵ�ľ���",
                "���ƿ�����Բ",
                "����ʵ����Բ",
                "���ƿ���Բ��",
                "����ʵ��Բ��",
                "���ƿ���Բ�Ǿ���",
                "����ʵ��Բ�Ǿ���",
                "��Ƥ��",
                "ѡ����ɫ",
                "����������ϸ",
                "��������"
              };

  JToolBar buttonPanel ;		       //���尴ť���
  private JLabel statusBar;            //��ʾ���״̬����ʾ��
  private DrawPanel drawingArea;       //��ͼ����
  private int width=850,height=550;    
  drawings[] itemList=new drawings[5000]; //������Ż���ͼ�ε�����
  private int currentChoice=3;            //����Ĭ�ϻ�ͼ״̬Ϊ��ʻ�
  int index=0;                         //��ǰ�Ѿ����Ƶ�ͼ����Ŀ
  private Color color=Color.black;     //��ǰ������ɫ
  int R,G,B;                           //������ŵ�ǰɫ��ֵ

  int f1,f2;                  //������ŵ�ǰ������
  String style1;              //������ŵ�ǰ����
  private float stroke=1.0f;  //���û��ʴ�ϸ��Ĭ��ֵΪ1.0f
  JCheckBox bold,italic;      //boldΪ���壬italicΪб�壬���߿���ͬʱʹ��
  JComboBox styles;
  public HB()      
  {
   super("С����");
   JMenuBar bar=new JMenuBar();		//����˵���
   JMenu fileMenu=new JMenu("�ļ�");
   //fileMenu.setMnemonic('F');
//�½��ļ��˵���
   JMenuItem newItem=new JMenuItem("�½�");
   newItem.addActionListener(
          new ActionListener(){
                  public void actionPerformed(ActionEvent e)
                  {
                   newFile();		//�����������������½��ļ�������
                  }
          }
   );
   fileMenu.add(newItem);

//�����ļ��˵���
   JMenuItem saveItem=new JMenuItem("����");
   saveItem.addActionListener(
          new ActionListener(){
                  public void actionPerformed(ActionEvent e)
                  {
                   saveFile();		//���������������ñ����ļ�������
                  }
          }
   );
   fileMenu.add(saveItem);
//���ļ��˵���
   JMenuItem loadItem=new JMenuItem("��");
   loadItem.addActionListener(
          new ActionListener(){
                  public void actionPerformed(ActionEvent e)
                  {
                   loadFile();		//���������������ô��ļ�������
                  }
          }
   );
   fileMenu.add(loadItem);
   fileMenu.addSeparator();
//�˳��˵���
   JMenuItem exitItem=new JMenuItem("�˳�");
   exitItem.addActionListener(
          new ActionListener(){
                  public void actionPerformed(ActionEvent e)
                  {
                   System.exit(0);	//��������������˳���ͼ�����
                  }
          }
   );
   fileMenu.add(exitItem);
   bar.add(fileMenu);
//������ɫ�˵���
   JMenu colorMenu=new JMenu("��ɫ");
//ѡ����ɫ�˵���
   JMenuItem colorItem=new JMenuItem("ѡ����ɫ");
   colorItem.addActionListener(
           new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                   {
                    chooseColor();	//����������������ѡ����ɫ������
                   }
       }
      );
   colorMenu.add(colorItem);
   bar.add(colorMenu);
//����������ϸ�˵���
    JMenu strokeMenu=new JMenu("������ϸ");
//����������ϸ�˵���
    JMenuItem strokeItem=new JMenuItem("����������ϸ");
    strokeItem.addActionListener(
           new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                    {
                     setStroke();
                     }
                   }
              );
           strokeMenu.add(strokeItem);
           bar.add(strokeMenu);
//������ʾ�˵���
    JMenu helpMenu=new JMenu("����");
//������ʾ�˵���
    JMenuItem aboutItem=new JMenuItem("���ڻ���");
    aboutItem.addActionListener(
           new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                    {
                     JOptionPane.showMessageDialog(null,
                        "С����\nJAVA����С��\n��2014��12��16�գ�����ʦ����ѧ���ѧԺ ",
                        " ��ͼ��˵�� ",
                         JOptionPane.INFORMATION_MESSAGE );
                     }
                   }
              );
    helpMenu.add(aboutItem);
    bar.add(helpMenu);
    items=new ImageIcon[names.length];
//�������ֻ���ͼ�εİ�ť
    drawingArea=new DrawPanel();
    choices=new JButton[names.length];
    buttonPanel = new JToolBar( JToolBar.VERTICAL ) ;
    buttonPanel = new JToolBar( JToolBar.HORIZONTAL) ;
    ButtonHandler handler=new ButtonHandler();
    ButtonHandler1 handler1=new ButtonHandler1();
//����������Ҫ��ͼ��ͼ�꣬��Щͼ�궼�������Դ�ļ���ͬ��Ŀ¼����
    for(int i=0;i<choices.length;i++)
    {
     items[i]=new ImageIcon("./src/XHB/"+names[i] + ".gif");
     choices[i]=new JButton("",items[i]);
     choices[i].setToolTipText(tipText[i]);
     buttonPanel.add(choices[i]);
    }
//���������������밴ť����
    for(int i=3;i<choices.length-3;i++)
    {
     choices[i].addActionListener(handler);
    }
    choices[0].addActionListener(
          new ActionListener(){
                  public void actionPerformed(ActionEvent e)
                  {
                   newFile();
                  }
          }
     );
    choices[1].addActionListener(
          new ActionListener(){
                  public void actionPerformed(ActionEvent e)
                  {
                   loadFile();
                  }
          }
     );
    choices[2].addActionListener(
          new ActionListener(){
                  public void actionPerformed(ActionEvent e)
                  {
                   saveFile();
                  }
          }
     );
    choices[choices.length-3].addActionListener(handler1);
    choices[choices.length-2].addActionListener(handler1);
    choices[choices.length-1].addActionListener(handler1);

//������ѡ��
    styles=new JComboBox(styleNames);
    styles.setMaximumRowCount(8);
    styles.addItemListener(
            new ItemListener(){
                    public void itemStateChanged(ItemEvent e)
                    {
                      style1=styleNames[styles.getSelectedIndex()];
                    }
               }
            );
//����ѡ��
    bold=new JCheckBox("����");
    italic=new JCheckBox("б��");

    checkBoxHandler cHandler=new checkBoxHandler();
    bold.addItemListener(cHandler);
    italic.addItemListener(cHandler);

    JPanel wordPanel=new JPanel();
    buttonPanel.add(bold);
    buttonPanel.add(italic);
    buttonPanel.add(styles);
    styles.setMinimumSize(  new Dimension ( 50, 20 ) );
    styles.setMaximumSize(new Dimension ( 100, 20 ) );

    Container c=getContentPane();
    super.setJMenuBar( bar );
    c.add(buttonPanel,BorderLayout.NORTH);
    c.add(drawingArea,BorderLayout.CENTER);

    statusBar=new JLabel();
    c.add(statusBar,BorderLayout.SOUTH);
    statusBar.setText(" ��ӭʹ��С����");

    createNewItem();
    setSize(width,height);
    show();
  }


//��ť������ButtonHanler�࣬�ڲ��࣬��������������ť�Ĳ���
public class ButtonHandler implements ActionListener
 {
  public void actionPerformed(ActionEvent e)
  {
   for(int j=3;j<choices.length-3;j++)
   {
      if(e.getSource()==choices[j])
         {currentChoice=j;
          createNewItem();
          repaint();}
        }
    }
 }

//��ť������ButtonHanler1�࣬����������ɫѡ�񡢻��ʴ�ϸ���á��������밴ť�Ĳ���
public class ButtonHandler1 implements ActionListener
 {
  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource()==choices[choices.length-3])
         {chooseColor();}
    if(e.getSource()==choices[choices.length-2])
         {setStroke();}
    if(e.getSource()==choices[choices.length-1])
         {JOptionPane.showMessageDialog(null,
             "������ͼ��ѡ�������ı���λ��",
             "��ʾ",JOptionPane.INFORMATION_MESSAGE );
          currentChoice=14;
          createNewItem();
          repaint();
          }
    }
 }


//����¼�mouseA�࣬�̳���MouseAdapter��������������Ӧ�¼�����
 class mouseA extends MouseAdapter
 {
   public void mousePressed(MouseEvent e)
    {statusBar.setText("��갴��λ��:[" + 
    	e.getX() + ", " + e.getY() + "]");//����״̬��ʾ

     itemList[index].x1=itemList[index].x2=e.getX();
     itemList[index].y1=itemList[index].y2=e.getY();

    //�����ǰѡ���ͼ������ʻ�������Ƥ�������������Ĳ���
    if(currentChoice==3||currentChoice==13)
    {
     itemList[index].x1=itemList[index].x2=e.getX();
     itemList[index].y1=itemList[index].y2=e.getY();
     index++;
     createNewItem();
     }

    //�����ǰѡ���ͼ��ʽ�������룬������������
     if(currentChoice==14)
     {
      itemList[index].x1=e.getX();
      itemList[index].y1=e.getY();

      String input;
      input=JOptionPane.showInputDialog(
          "�������ı�");
      itemList[index].s1=input;
      itemList[index].x2=f1;
      itemList[index].y2=f2;
      itemList[index].s2=style1;

      index++;
      currentChoice=14;
      createNewItem();
      drawingArea.repaint();
      }
    }

   public void mouseReleased(MouseEvent e)
    {statusBar.setText("����ɿ�λ��:[" + 
    	e.getX() + ", " + e.getY() + "]");

    if(currentChoice==3||currentChoice==13)
    {
     itemList[index].x1=e.getX();
     itemList[index].y1=e.getY();
    }
     itemList[index].x2=e.getX();
     itemList[index].y2=e.getY();
     repaint();
     index++;
     createNewItem();
    }

   public void mouseEntered(MouseEvent e)
   {statusBar.setText("������λ��:[" + 
    	e.getX() + ", " + e.getY() + "]");
           }

   public void mouseExited(MouseEvent e)
   {
          statusBar.setText("����˳�λ��:[" + 
    	e.getX() + ", " + e.getY() + "]");
           }
  }


//����¼�mouseB��̳���MouseMotionAdapter�������������϶�������ƶ�ʱ����Ӧ����
 class mouseB extends MouseMotionAdapter
 {
  public void mouseDragged(MouseEvent e)
  {statusBar.setText("����϶�λ��:[" + 
    	e.getX() + ", " + e.getY() + "]");

   if(currentChoice==3||currentChoice==13)
   {
    itemList[index-1].x1=itemList[index].x2=itemList[index].x1=e.getX();
    itemList[index-1].y1=itemList[index].y2=itemList[index].y1=e.getY();
    index++;
    createNewItem();
   }
   else
    {
     itemList[index].x2=e.getX();
     itemList[index].y2=e.getY();
    }
   repaint();
   }

  public void mouseMoved(MouseEvent e)
   {statusBar.setText("����ƶ�λ��:[" + 
    	e.getX() + ", " + e.getY() + "]");}
  }


//ѡ��������ʱ���õ����¼��������࣬���뵽�������ѡ�����
private class checkBoxHandler implements ItemListener
 {
  public void itemStateChanged(ItemEvent e)
  {
   if(e.getSource()==bold)
     if(e.getStateChange()==ItemEvent.SELECTED)
        f1=Font.BOLD;
     else
        f1=Font.PLAIN;
   if(e.getSource()==italic)
     if(e.getStateChange()==ItemEvent.SELECTED)
        f2=Font.ITALIC;
     else
        f2=Font.PLAIN;
          }
 }


//��ͼ����࣬������ͼ
 class DrawPanel extends JPanel
 {
   public DrawPanel()
  {
   setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
   setBackground(Color.white);
   addMouseListener(new mouseA());
   addMouseMotionListener(new mouseB());
  }

    public void paintComponent(Graphics g)
    {
      super.paintComponent(g);

      Graphics2D g2d=(Graphics2D)g;	//���廭��

      int j=0;
      while (j<=index)
      {
        draw(g2d,itemList[j]);
        j++;
      }
    }

    void draw(Graphics2D g2d,drawings i)
    {
      i.draw(g2d);//�����ʴ��뵽���������У�������ɸ��ԵĻ�ͼ
    }
 }


//�½�һ����ͼ������Ԫ����ĳ����
 void createNewItem()
  { if(currentChoice==14)//������Ӧ���α�����
          drawingArea.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
    else
          drawingArea.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

    switch (currentChoice)
    {
      case 3:
        itemList[index]=new Pencil();
        break;
      case 4:
        itemList[index]=new Line();
        break;
      case 5:
        itemList[index]=new Rect();
        break;
      case 6:
        itemList[index]=new fillRect();
        break;
      case 7:
        itemList[index]=new Oval();
        break;
      case 8:
        itemList[index]=new fillOval();
        break;
      case 9:
        itemList[index]=new Circle();
        break;
      case 10:
        itemList[index]=new fillCircle();
        break;
      case 11:
        itemList[index]=new RoundRect();
        break;
      case 12:
        itemList[index]=new fillRoundRect();
        break;
      case 13:
        itemList[index]=new Rubber();
        break;
      case 14:
        itemList[index]=new Word();
        break;
    }
    itemList[index].type=currentChoice;
    itemList[index].R=R;
    itemList[index].G=G;
    itemList[index].B=B;
    itemList[index].stroke=stroke;
  }


//ѡ��ǰ��ɫ�����
public void chooseColor()
 {
    color=JColorChooser.showDialog(HB.this,
                          "��ѡ��һ����ɫ",color);
    R=color.getRed();
    G=color.getGreen();
    B=color.getBlue();
    itemList[index].R=R;
    itemList[index].G=G;
    itemList[index].B=B;
  }

//ѡ��ǰ������ϸ�����
public void setStroke()
 {
  String input;
  input=JOptionPane.showInputDialog(
          "������һ��������������ϸֵ ( >0 )");
  stroke=Float.parseFloat(input);
  itemList[index].stroke=stroke;
  }

//����ͼ���ļ������
 public void saveFile()
 {
    JFileChooser fileChooser=new JFileChooser();
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    int result =fileChooser.showSaveDialog(this);
    if(result==JFileChooser.CANCEL_OPTION)
             return ;
    File fileName=fileChooser.getSelectedFile();
    fileName.canWrite();

    if (fileName==null||fileName.getName().equals(""))
    JOptionPane.showMessageDialog(fileChooser,"�ļ�����Ч",
            "�ļ�����Ч", JOptionPane.ERROR_MESSAGE);
    else{
      try {
       fileName.delete();
       FileOutputStream fos=new FileOutputStream(fileName);

       output=new ObjectOutputStream(fos);
       drawings record;

       output.writeInt( index );

       for(int i=0;i< index ;i++)
       {
        drawings p= itemList[ i ] ;
        output.writeObject(p);
        output.flush();    //������ͼ����Ϣǿ��ת���ɸ������Ի��洢���ļ���
        }
      output.close();
      fos.close();
      }
       catch(IOException ioe)
       {
         ioe.printStackTrace();
       }
      }
   }

//��һ��ͼ���ļ�����Σ�loadFile����ͨ������FileInputStream��������ļ�
 public void loadFile()
 {

    JFileChooser fileChooser=new JFileChooser();
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    int result =fileChooser.showOpenDialog(this);
    if(result==JFileChooser.CANCEL_OPTION)
          return ;
    File fileName=fileChooser.getSelectedFile();
    fileName.canRead();
    if (fileName==null||fileName.getName().equals(""))
       JOptionPane.showMessageDialog(fileChooser,"�ļ�����Ч",
            "�ļ�����Ч", JOptionPane.ERROR_MESSAGE);
    else {
      try {

          FileInputStream fis=new FileInputStream(fileName);

          input=new ObjectInputStream(fis);
          drawings inputRecord;

          int countNumber=0;
          countNumber=input.readInt();

          for(index=0;index< countNumber ;index++)
          {
            inputRecord=(drawings)input.readObject();
            itemList[ index ] = inputRecord ;

          }

          createNewItem();
          input.close();

          repaint();
          }
           catch(EOFException endofFileException){
            JOptionPane.showMessageDialog(this,"�ļ���û�и���ļ�¼",
                           "�޷��ҵ���",JOptionPane.ERROR_MESSAGE );
          }
          catch(ClassNotFoundException classNotFoundException){
            JOptionPane.showMessageDialog(this,"���ܴ�������",
                           "�ļ�����",JOptionPane.ERROR_MESSAGE );
          }
          catch (IOException ioException){
            JOptionPane.showMessageDialog(this,"���ļ���ȡ���ϳ���",
                           "��������",JOptionPane.ERROR_MESSAGE );
            }
          }
       }


//�½�һ���ļ������
 public void newFile()
 {
  index=0;
  currentChoice=3;
  color=Color.black;
  stroke=1.0f;
  createNewItem();
  repaint();//���й�ֵ����Ϊ��ʼ״̬�������ػ�
 }



//��������
 public static void main(String args[])
  {try {
        UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName());
        }
     catch ( Exception e ) {}//����������Ϊ��ǰwindows���

   HB newPad=new HB();
   newPad.addWindowListener(
        new WindowAdapter(){
           public void windowClosing(WindowEvent e)
           {System.exit(0);}});
  }


//���廭ͼ�Ļ���ͼ�ε�Ԫ
class drawings implements Serializable//���࣬����ͼ�ε�Ԫ���õ����л��ӿڣ�����ʱ����
 {
  int x1,y1,x2,y2;	//������������
  int R,G,B;		//����ɫ������
  float stroke;		//����������ϸ����
  int type;		//������������
  String s1;
  String s2;		//��������������

  void draw(Graphics2D g2d){};//�����ͼ����
 }


/*************************************************
  �����Ǹ��ֻ���ͼ�ε�Ԫ�����࣬���̳��Ը���drawings
**************************************************/

 class Line extends drawings //ֱ����
 {
 void draw(Graphics2D g2d)
  {
  	  g2d.setPaint(new Color(R,G,B));
      g2d.setStroke(new BasicStroke(stroke,
                BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL));
      g2d.drawLine(x1,y1,x2,y2);
   }
 }


 class Rect extends drawings//������
 {
 void draw(Graphics2D g2d)
  {
  	  g2d.setPaint(new Color(R,G,B));
      g2d.setStroke(new BasicStroke(stroke));
      g2d.drawRect(Math.min(x1,x2),Math.min(y1,y2),
              Math.abs(x1-x2),Math.abs(y1-y2));
  }
 }


 class fillRect extends drawings//ʵ�ľ�����
 {
 void draw(Graphics2D g2d)
  {
  	  g2d.setPaint(new Color(R,G,B));
      g2d.setStroke(new BasicStroke(stroke));
      g2d.fillRect(Math.min(x1,x2),Math.min(y1,y2),
              Math.abs(x1-x2),Math.abs(y1-y2));
  }
 }


 class Oval extends drawings//��Բ��
  {
    void draw(Graphics2D g2d)
    {
    	  g2d.setPaint(new Color(R,G,B));
        g2d.setStroke(new BasicStroke(stroke));
        g2d.drawOval(Math.min(x1,x2),Math.min(y1,y2),
                  Math.abs(x1-x2),Math.abs(y1-y2));
    }
  }


 class fillOval extends drawings//ʵ����Բ
 {
  void draw(Graphics2D g2d)
  {
  	  g2d.setPaint(new Color(R,G,B));
      g2d.setStroke(new BasicStroke(stroke));
      g2d.fillOval(Math.min(x1,x2),Math.min(y1,y2),
                Math.abs(x1-x2),Math.abs(y1-y2));
         }
 }


 class Circle extends drawings//Բ��
 {
   void draw(Graphics2D g2d)
   {
   	   g2d.setPaint(new Color(R,G,B));
       g2d.setStroke(new BasicStroke(stroke));
       g2d.drawOval(Math.min(x1,x2),Math.min(y1,y2),
               Math.max(Math.abs(x1-x2),Math.abs(y1-y2)),
               Math.max(Math.abs(x1-x2),Math.abs(y1-y2))
               );
    }
 }


 class fillCircle extends drawings//ʵ��Բ
 {
  void draw(Graphics2D g2d)
  {
  	  g2d.setPaint(new Color(R,G,B));
      g2d.setStroke(new BasicStroke(stroke));
      g2d.fillOval(Math.min(x1,x2),Math.min(y1,y2),
               Math.max(Math.abs(x1-x2),Math.abs(y1-y2)),
               Math.max(Math.abs(x1-x2),Math.abs(y1-y2))
               );
  }
 }


 class RoundRect extends drawings//Բ�Ǿ�����
 {
  void draw(Graphics2D g2d)
  {
  	  g2d.setPaint(new Color(R,G,B));
      g2d.setStroke(new BasicStroke(stroke));
      g2d.drawRoundRect(Math.min(x1,x2),Math.min(y1,y2),
                   Math.abs(x1-x2),Math.abs(y1-y2),
                   50,35);
  }
 }


 class fillRoundRect extends drawings//ʵ��Բ�Ǿ�����
 {
  void draw(Graphics2D g2d)
  {
  	  g2d.setPaint(new Color(R,G,B));
      g2d.setStroke(new BasicStroke(stroke));
      g2d.fillRoundRect(Math.min(x1,x2),Math.min(y1,y2),
                   Math.abs(x1-x2),Math.abs(y1-y2),
                   50,35);
  }
 }


 class Pencil extends drawings//��ʻ���
 {
  void draw(Graphics2D g2d)
  {
  	 g2d.setPaint(new Color(R,G,B));
     g2d.setStroke(new BasicStroke(stroke,
                BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL));
     g2d.drawLine(x1,y1,x2,y2);
  }
 }


 class Rubber extends drawings//��Ƥ����
 {
  void draw(Graphics2D g2d)
  {
     g2d.setPaint(new Color(255,255,255));
     g2d.setStroke(new BasicStroke(stroke+4,
                BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL));
     g2d.drawLine(x1,y1,x2,y2);
   }
 }


 class Word extends drawings//����������
 {
  void draw(Graphics2D g2d)
  {    
     g2d.setPaint(new Color(R,G,B));
     g2d.setFont(new Font(s2,x2+y2,((int)stroke)*18));
     //�鿴���������е�����
     GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();  
     String[] fontName = e.getAvailableFontFamilyNames();  
	 for(int i = 0; i<fontName.length ; i++)  {  
	     System.out.println(fontName[i]);  
	 }
     if (s1!= null )
     g2d.drawString(s1,x1,y1);
  }

 }
 }

