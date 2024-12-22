import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Scanner;

public class GameData implements Serializable {
	// 游戏的一些基础参数
	int L, H, W, currentLevel;  // 层数、高度、宽度、当前层级
	int pX, pY, heroHealth, keyNum;  // 角色位置（pX, pY）、角色健康值、钥匙数量
	int[][][] map;  // 三维数组，存储地图数据

	// 读取地图文件
	void readMapFromFile(String filePath) {
		currentLevel = 0;  // 默认从第0层开始
		heroHealth = 105;  // 初始生命值
		keyNum = 0;  // 初始钥匙数量
		pX = 3;  // 默认角色起始位置X
		pY = 3;  // 默认角色起始位置Y
		try {
			Scanner in = new Scanner(new File(filePath));
			L = in.nextInt();  // 层数
			H = in.nextInt();  // 高度
			W = in.nextInt();  // 宽度
			map = new int[L][H][W];  // 初始化地图

			// 读取每层的地图数据
			for (int i = 0; i < L; i++) {
				for (int j = 0; j < H; j++) {
					for (int k = 0; k < W; k++) {
						map[i][j][k] = in.nextInt();  // 读取每个位置的地图元素
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Error with files: " + e.toString());
		}
	}

	// 打印当前层的地图信息
	void printMap() {
		char[] C = { 'W', '_', 'K', 'D', 'S', 'E', 'H' };  // 地图元素的字符表示
		for (int j = 0; j < H; j++) {
			for (int k = 0; k < W; k++) {
				if (map[currentLevel][j][k] < 0) {
					System.out.print("M ");  // 如果是怪物
				} else if (map[currentLevel][j][k] > 10) {
					System.out.print("P ");  // 如果是药水等特殊物品
				} else {
					System.out.print(C[map[currentLevel][j][k]] + " ");  // 正常元素（墙、地板、钥匙等）
				}
			}
			System.out.print("\n\n");
		}
		System.out.print("Health:" + heroHealth + "  KeyNum:" + keyNum + "  Menu: press 0\n");
	}

	// 复制另一个 GameData 对象的数据到当前对象
	void copyFields(Object source) {
		try {
			Class<?> clazz = this.getClass();
			Field[] fields = clazz.getDeclaredFields();  // 获取当前类的所有字段
			for (Field field : fields) {
				field.setAccessible(true);  // 设置字段可以被访问
				Object value = field.get(source);  // 获取源对象中的字段值
				field.set(this, value);  // 设置当前对象的字段为源对象的字段值
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();  // 捕获并打印反射异常
		}
	}

	// 移动角色的方法
	public void moveHero(int newX, int newY) {
		// 判断新位置是否越界
		if (newX >= 0 && newY >= 0 && newX < H && newY < W) {
			if (map[currentLevel][newX][newY] != 0) {  // 0代表空地，可以移动
				pX = newX;
				pY = newY;
			}
		}
	}

	// 获取角色当前的地图元素
	public int getCurrentElement() {
		return map[currentLevel][pX][pY];
	}

	// 更新角色的状态，比如碰到怪物、门等事件
	public void handleEventAtPosition() {
		int element = map[currentLevel][pX][pY];
		switch (element) {
			case 2:  // K - 键
				keyNum++;
				break;
			case 3:  // D - 门
				if (keyNum > 0) {
					keyNum--;
					break;
				}
				// 其他情况无法打开门
			case 4:  // S - 楼梯
				currentLevel++;
				break;
			case 5:  // E - 出口
				System.out.println("You Win!");
				System.exit(0);  // 游戏结束
				break;
			case 6:  // H - 英雄
				heroHealth += 10;
				break;
			case -1:  // M - 怪物
				heroHealth -= 10;
				if (heroHealth <= 0) {
					System.out.println("You Lost! Game Over.");
					System.exit(0);  // 游戏结束
				}
				break;
		}
	}

	// 获取并返回地图的元素状态，用字符表示
	public String getElementAsString(int element) {
		String[] elementStrings = {"Wall", "Floor", "Key", "Door", "Stair", "Exit", "Hero", "Potion", "Monster"};
		if (element < 0) {
			return "Monster";
		} else if (element > 10) {
			return "Potion";
		} else {
			return elementStrings[element];
		}
	}
}
