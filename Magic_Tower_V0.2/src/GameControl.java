import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameControl {
	GameData gameData;
	Menu menu;
	GUI gui;

	GameControl(GameData gameData, Menu menu, GUI gui) {
		this.gameData = gameData;
		this.menu = menu;
		this.gui = gui;
	}

	void gameStart() {
		// 设置按钮事件监听器来响应按钮的点击
		gui.buttonUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleInput('w');
				gui.refreshGUI();
			}
		});

		gui.buttonDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleInput('s');
				gui.refreshGUI();
			}
		});

		gui.buttonLeft.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleInput('a');
				gui.refreshGUI();
			}
		});

		gui.buttonRight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleInput('d');
				gui.refreshGUI();
			}
		});
	}

	// 处理输入的字符，并根据当前地图执行相应的动作
	void handleInput(char inC) {
		int tX = gameData.pX, tY = gameData.pY;

		if (inC == 'a') tY--;
		if (inC == 's') tX++;
		if (inC == 'd') tY++;
		if (inC == 'w') tX--;

		if (gameData.map[gameData.currentLevel][tX][tY] == 2) {  // 键
			gameData.keyNum++;
			moveHero(tX, tY);
		} else if (gameData.map[gameData.currentLevel][tX][tY] == 3 && gameData.keyNum > 0) {  // 门
			gameData.keyNum--;
			moveHero(tX, tY);
		} else if (gameData.map[gameData.currentLevel][tX][tY] == 4) {  // 楼梯
			gameData.map[gameData.currentLevel][gameData.pX][gameData.pY] = 1;
			gameData.currentLevel++;
			for (int i = 0; i < gameData.H; i++)
				for (int j = 0; j < gameData.W; j++)
					if (gameData.map[gameData.currentLevel][i][j] == 6) {
						gameData.pX = i;
						gameData.pY = j;
					}
		} else if (gameData.map[gameData.currentLevel][tX][tY] == 5) {  // 胜利
			System.out.print("You Win!!");
			System.exit(0);
		} else if (gameData.map[gameData.currentLevel][tX][tY] > 10) {  // 药水
			gameData.heroHealth += gameData.map[gameData.currentLevel][tX][tY];
			moveHero(tX, tY);
		} else if (gameData.map[gameData.currentLevel][tX][tY] == 1) {  // 地板
			moveHero(tX, tY);
		} else if (gameData.map[gameData.currentLevel][tX][tY] < 0) {  // 怪物
			if (gameData.map[gameData.currentLevel][tX][tY] + gameData.heroHealth <= 0) {
				System.out.print("That monster has " + (-gameData.map[gameData.currentLevel][tX][tY]) + " power, You Lose!!");
				System.exit(0);
			} else {
				gameData.heroHealth += gameData.map[gameData.currentLevel][tX][tY];
				moveHero(tX, tY);
			}
		}
	}

	void moveHero(int tX, int tY) {
		gameData.map[gameData.currentLevel][gameData.pX][gameData.pY] = 1;  // 更新原位置为地板
		gameData.map[gameData.currentLevel][tX][tY] = 6;  // 更新新位置为英雄
		gameData.pX = tX;
		gameData.pY = tY;
	}
}
