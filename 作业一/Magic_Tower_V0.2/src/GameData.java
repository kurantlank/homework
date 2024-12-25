import java.io.Serializable;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class GameData implements Serializable {
	int L, H, W, currentLevel;
	int pX, pY, heroHealth, keyNum;
	int[][][] map;

	// 新增属性：玩家的攻击力、怪物的血量和攻击力
	int heroAttack;
	int monsterHealth;
	int monsterAttack;

	// 根据难度设置属性
	public void setDifficulty(String difficulty) {
		switch (difficulty) {
			case "easy":
				heroHealth = 150;   // Easy模式玩家血量
				heroAttack = 30;    // Easy模式玩家攻击力
				monsterHealth = 50; // Easy模式怪物血量
				monsterAttack = 10;  // Easy模式怪物攻击力

				break;
			case "medium":
				heroHealth = 100;   // Medium模式玩家血量
				heroAttack = 20;    // Medium模式玩家攻击力
				monsterHealth = 100;// Medium模式怪物血量
				monsterAttack = 50; // Medium模式怪物攻击力

				break;
			case "hard":
				heroHealth = 100;    // Hard模式玩家血量
				heroAttack = 10;    // Hard模式玩家攻击力
				monsterHealth = 200;// Hard模式怪物血量
				monsterAttack = 90; // Hard模式怪物攻击力

				break;
			default:
				throw new IllegalArgumentException("Unknown difficulty: " + difficulty);
		}
	}

	// 读取地图数据
	void readMapFromFile(String filePath) {
		currentLevel = 0;
		keyNum = 0;
		pX = 3;
		pY = 3;
		try {
			Scanner in = new Scanner(new File(filePath));
			L = in.nextInt();
			H = in.nextInt();
			W = in.nextInt();
			map = new int[L][H][W];
			for (int i = 0; i < L; i++)
				for (int j = 0; j < H; j++)
					for (int k = 0; k < W; k++)
						map[i][j][k] = in.nextInt();
		} catch (IOException e) {
			System.out.println("Error with files:" + e.toString());
		}
	}

	// 打印地图
	void printMap() {
		char C[] = { 'W', '_', 'K', 'D', 'S', 'E', 'H' };
		for (int j = 0; j < H; j++) {
			for (int k = 0; k < W; k++) {
				if (map[currentLevel][j][k] < 0)
					System.out.print("M ");
				else if (map[currentLevel][j][k] > 10)
					System.out.print("P ");
				else
					System.out.print(C[map[currentLevel][j][k]] + " ");
			}
			System.out.print("\n\n");
		}
		System.out.print("Health:" + Integer.toString(heroHealth) + "  KeyNum:" + Integer.toString(keyNum) + "  Menu:press 0\n");
	}
}
