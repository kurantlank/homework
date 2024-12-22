import java.awt.*;
import javax.swing.*;

public class GUI {
	GameData gameData;
	JFrame f;
	JLabel[][] b;
	JButton buttonUp, buttonDown, buttonLeft, buttonRight;

	GUI(GameData gameData) {
		this.gameData = gameData;
		f = new JFrame("Magic Tower");
		b = new JLabel[gameData.H][gameData.W];

		// 创建地图的 JLabel
		for (int i = 0; i < gameData.H; i++) {
			for (int j = 0; j < gameData.W; j++) {
				b[i][j] = new JLabel();
				b[i][j].setBounds(j * 100, i * 100, 100, 100);
				f.add(b[i][j]);
			}
		}

		// 创建按钮
		buttonUp = createButton("W");
		buttonDown = createButton("S");
		buttonLeft = createButton("A");
		buttonRight = createButton("D");

		// 创建一个面板用于按钮布局
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(3, 3)); // 3x3 网格布局
		buttonPanel.setBounds(10, gameData.H * 100 + 10, 270, 90); // 设置按钮面板位置

		// 添加按钮到面板
		buttonPanel.add(new JLabel()); // 空标签用于占位
		buttonPanel.add(buttonUp);      // W
		buttonPanel.add(new JLabel()); // 空标签用于占位
		buttonPanel.add(buttonLeft);    // A
		buttonPanel.add(buttonDown);     // S
		buttonPanel.add(buttonRight);    // D
		buttonPanel.add(new JLabel()); // 空标签用于占位
		buttonPanel.add(new JLabel()); // 空标签用于占位
		buttonPanel.add(new JLabel()); // 空标签用于占位

		// 将按钮面板添加到框架
		f.add(buttonPanel);

		f.setSize(gameData.W * 100 + 20, gameData.H * 100 + 130); // 调整高度以适应按钮面板
		f.setLayout(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		refreshGUI();
	}

	private JButton createButton(String text) {
		JButton button = new JButton(text);
		button.setFont(new Font("Arial", Font.BOLD, 20));
		button.setBackground(new Color(100, 149, 237)); // Cornflower Blue
		button.setForeground(Color.WHITE); // White text
		button.setBorder(BorderFactory.createEtchedBorder()); // Add a border
		button.setFocusPainted(false); // Remove focus rectangle
		button.setOpaque(true); // Enable opaque to show background color
		button.setPreferredSize(new Dimension(80, 30));

		// 添加鼠标悬停效果
		button.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				button.setBackground(new Color(70, 130, 180)); // Steel Blue
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				button.setBackground(new Color(100, 149, 237)); // Revert back
			}
		});

		return button;
	}

	public void refreshGUI() {
		for (int i = 0; i < gameData.H; i++) {
			for (int j = 0; j < gameData.W; j++) {
				Image scaledImage = chooseImage(gameData.map[gameData.currentLevel][i][j]);
				b[i][j].setIcon(new ImageIcon(scaledImage));
			}
		}
	}

	private static Image chooseImage(int index) {
		ImageIcon[] icons = new ImageIcon[10];
		Image scaledImage;
		icons[0] = new ImageIcon("Wall.jpg");
		icons[1] = new ImageIcon("Floor.jpg");
		icons[2] = new ImageIcon("Key.jpg");
		icons[3] = new ImageIcon("Door.jpg");
		icons[4] = new ImageIcon("Stair.jpg");
		icons[5] = new ImageIcon("Exit.jpg");
		icons[6] = new ImageIcon("Hero.jpg");
		icons[7] = new ImageIcon("Potion.jpg");
		icons[8] = new ImageIcon("Monster.jpg");

		if (index > 10)
			scaledImage = icons[7].getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		else if (index < 0)
			scaledImage = icons[8].getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		else
			scaledImage = icons[index].getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		return scaledImage;
	}
}
