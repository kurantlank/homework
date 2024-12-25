import java.awt.Image;
import javax.swing.*;
import java.awt.*;

public class GUI {
	GameData gameData;
	JFrame f;
	JLabel[][] b;

	// 构造方法
	GUI(GameData gameData) {
		this.gameData = gameData;
		f = new JFrame("Magic Tower");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new GridLayout(gameData.H, gameData.W)); // 使用GridLayout布局管理器

		// 初始化格子
		b = new JLabel[gameData.H][gameData.W];
		for (int i = 0; i < gameData.H; i++) {
			for (int j = 0; j < gameData.W; j++) {
				b[i][j] = new JLabel();
				// 设置标签的边框，用于查看标签的位置
				b[i][j].setPreferredSize(new Dimension(100, 100));
				f.add(b[i][j]);
			}
		}
		f.setSize(gameData.W * 100 + 10, gameData.H * 100 + 40);
		f.setVisible(true);
		refreshGUI(); // 确保初始时就刷新GUI
	}

	// 刷新图标
	public void refreshGUI() {
		for (int i = 0; i < gameData.H; i++) {
			for (int j = 0; j < gameData.W; j++) {
				// 获取对应的图标
				Image scaledImage = chooseImage(gameData.map[gameData.currentLevel][i][j]);
				// 设置图标
				b[i][j].setIcon(new ImageIcon(scaledImage));
			}
		}
	}

	// 根据地图值选择对应的图标
	private static Image chooseImage(int index) {
		ImageIcon[] icons = new ImageIcon[10];
		Image scaledImage;

		// 这里加载图标，根据实际文件路径
		icons[0] = new ImageIcon("Wall.jpg");
		icons[1] = new ImageIcon("Floor.jpg");
		icons[2] = new ImageIcon("Key.jpg");
		icons[3] = new ImageIcon("Door.jpg");
		icons[4] = new ImageIcon("Stair.jpg");
		icons[5] = new ImageIcon("Exit.jpg");
		icons[6] = new ImageIcon("Hero.jpg");
		icons[7] = new ImageIcon("Potion.jpg");
		icons[8] = new ImageIcon("Monster.jpg");

		// 根据地图值选择图标
		if (index > 10) {
			// 玩家物品或药水
			scaledImage = icons[7].getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		} else if (index < 0) {
			// 怪物
			scaledImage = icons[8].getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		} else {
			// 普通地图元素
			scaledImage = icons[index].getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		}
		return scaledImage;
	}
}
