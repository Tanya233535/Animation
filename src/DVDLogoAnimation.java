import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class DVDLogoAnimation extends JPanel implements ActionListener {
    private int x = 0;
    private int y = 0;
    private int xSpeed = 4; // Скорость по оси X
    private int ySpeed = 4; // Скорость по оси Y
    private Color backgroundColor = Color.BLACK; // Цвет фона
    private Image logoImage; // Изображение логотипа
    private final int LOGO_WIDTH = 100; // Ширина логотипа
    private final int LOGO_HEIGHT = 50; // Высота логотипа
    private Color currentColor; // Текущий цвет логотипа

    public DVDLogoAnimation() {
        // Открываем диалог выбора файла для загрузки изображения
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберите изображение логотипа");
        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fileChooser.getSelectedFile();
            try {
                logoImage = ImageIO.read(fileToOpen); // Загружаем изображение
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Инициализируем случайный цвет
        currentColor = getRandomColor();

        Timer timer = new Timer(20, this); // Создаем таймер, который вызывает actionPerformed каждые 20 мс
        timer.start(); // Запускаем таймер
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(backgroundColor); // Устанавливаем фон
        if (logoImage != null) {
            // Применяем цветовой фильтр к логотипу
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(currentColor);
            g2d.setComposite(AlphaComposite.SrcAtop); // Позволяет наложить цвет на изображение
            g2d.fillRect(x, y, LOGO_WIDTH, LOGO_HEIGHT); // Закрашиваем прямоугольник под логотипом
            g2d.drawImage(logoImage, x, y, LOGO_WIDTH, LOGO_HEIGHT, this); // Рисуем логотип DVD
            g2d.dispose();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        x += xSpeed; // Обновляем положение по оси X
        y += ySpeed; // Обновляем положение по оси Y

        // Проверка на столкновение с границами панели
        if (x <= 0 || x >= getWidth() - LOGO_WIDTH) {
            xSpeed = -xSpeed; // Изменяем направление по оси X
            currentColor = getRandomColor(); // Генерируем новый цвет при столкновении
        }
        if (y <= 0 || y >= getHeight() - LOGO_HEIGHT) {
            ySpeed = -ySpeed; // Изменяем направление по оси Y
            currentColor = getRandomColor(); // Генерируем новый цвет при столкновении
        }

        repaint(); // Перерисовываем панель
    }

    private Color getRandomColor() {
        // Генерация случайного цвета
        Random rand = new Random();
        return new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("DVD Logo Animation");
        DVDLogoAnimation dvdLogo = new DVDLogoAnimation();
        frame.add(dvdLogo);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
