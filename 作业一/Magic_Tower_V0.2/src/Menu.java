import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.io.*;
import java.util.*;

public class Menu {
	Element rootElement;
	GameData gameData;

	// 构造器，传入GameData对象
	public Menu(GameData gameData) {
		this.gameData = gameData;
	}

	// 进入菜单
	public void enterMenu() {
		displayDifficultyMenu();  // 显示难度选择菜单
	}

	// 加载XML菜单配置
	public void loadMenu(String filePath) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(filePath));
			document.getDocumentElement().normalize();
			rootElement = document.getDocumentElement();
			removeTextNodes(rootElement);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 移除XML文档中的文本节点
	private void removeTextNodes(Node node) {
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node child = childNodes.item(i);
			if (child.getNodeType() == Node.TEXT_NODE) {
				String textContent = child.getTextContent().trim();
				if (textContent.isEmpty()) {
					node.removeChild(child);
					i--;
				}
			} else if (child.getNodeType() == Node.ELEMENT_NODE) {
				removeTextNodes(child);
			}
		}
	}

	// 显示难度选择菜单
	private void displayDifficultyMenu() {
		System.out.println("**** 选择游戏难度 ****");
		System.out.println("1. 简单");
		System.out.println("2. 中等");
		System.out.println("3. 困难");
		System.out.println("0. 退出游戏");
		System.out.print("请输入选择(1/2/3): ");

		Scanner scanner = new Scanner(System.in);
		int difficultyChoice = scanner.nextInt();

		// 根据选择的难度设置游戏难度
		switch (difficultyChoice) {
			case 1: // 简单
				gameData.setDifficulty("easy");
				break;
			case 2: // 中等
				gameData.setDifficulty("medium");
				break;
			case 3: // 困难
				gameData.setDifficulty("hard");
				break;
			case 0: // 退出
				System.out.println("退出游戏");
				System.exit(0);
				break;
			default:
				System.out.println("无效选择，返回菜单。");
				return;
		}

		System.out.println("选择完成，开始游戏！");
	}

	// 重新开始游戏
	public void restartGame() {
		gameData.readMapFromFile("Map.in");
		System.out.println("游戏已重新开始！");
	}

	// 退出游戏
	public void quitGame() {
		System.exit(0);
	}

	// 保存游戏
	public void saveGame() {
		try (FileOutputStream fileOut = new FileOutputStream("Game.ser");
			 ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
			out.writeObject(gameData);
			System.out.println("游戏已保存到 Game.ser");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 加载游戏
	public void loadGame() {
		try (FileInputStream fileIn = new FileInputStream("Game.ser");
			 ObjectInputStream in = new ObjectInputStream(fileIn)) {
			gameData = (GameData) in.readObject();
			System.out.println("游戏已从 Game.ser 中加载");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
