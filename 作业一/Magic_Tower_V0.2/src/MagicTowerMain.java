public class MagicTowerMain {
	static GameData gameData;
	static GameControl gameControl;
	static Menu menu;
	static GUI gui;

	public static void main(String[] args) {
		// 初始化 GameData 和 Menu
		gameData = new GameData();
		menu = new Menu(gameData);

		// 加载菜单文件 (此文件中应包含难度选择的选项)
		menu.loadMenu("Menu.XML");

		// 显示难度选择菜单
		menu.enterMenu();

		// 加载地图并初始化游戏数据
		gameData.readMapFromFile("Map.in");

		// 根据选择的难度调整游戏数据（例如血量、攻击力等）
		// 这个操作已经在 Menu 类中完成

		// 初始化 GUI
		gui = new GUI(gameData);

		// 初始化 GameControl
		gameControl = new GameControl(gameData, menu, gui);

		// 启动游戏
		gameControl.gameStart();
	}
}
