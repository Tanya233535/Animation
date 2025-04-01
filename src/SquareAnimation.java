import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class SquareAnimation extends JPanel implements ActionListener {
    private BufferedImage image;
    private Timer timer;
    private int x = 50; // Начальная позиция по X
    private int y = 50; // Начальная позиция по Y
    private int direction = 0; // Направление движения (0 - вправо, 1 - вниз, 2 - влево, 3 - вверх)
    private final int STEP = 5; // Шаг движения

    public SquareAnimation() {
        // Загрузка изображения через диалоговое окно выбора файла
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберите изображение");
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                image = ImageIO.read(selectedFile); // Загрузка изображения
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Изображение не выбрано. Программа будет завершена.");
            System.exit(0);
        }

        // Настройка таймера
        timer = new Timer(100, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE); // Установить белый фон
        if (image != null) {
            g.drawImage(image, x, y, this); // Нарисовать изображение
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Обновление позиции изображения
        // Получаем размеры панели
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        switch (direction) {
            case 0: // Вправо
                x += STEP;
                if (x + image.getWidth() >= panelWidth) direction = 1; // Если достигли правой границы
                break;
            case 1: // Вниз
                y += STEP;
                if (y + image.getHeight() >= panelHeight) direction = 2; // Если достигли нижней границы
                break;
            case 2: // Влево
                x -= STEP;
                if (x <= 0) direction = 3; // Если достигли левой границы
                break;
            case 3: // Вверх
                y -= STEP;
                if (y <= 0) direction = 0; // Если достигли верхней границы
                break;
        }
        repaint(); // Перерисовать компонент
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Square Animation");
        SquareAnimation animation = new SquareAnimation();
        frame.add(animation);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
