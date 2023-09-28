import java.io.*;
import java.util.*;

public class Main {

    public static int getIntInput(Scanner scanner, int end) {

        int actionNum = 0;

        boolean isInt = false;
        while (!isInt) {
            try {
                actionNum = scanner.nextInt();
                if (actionNum < 0) {
                    throw new NegativeInputException();
                }
                if (actionNum > end) {
                    throw new RangeException();
                }
                isInt = true;
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Введите число");
                scanner.nextLine();
            } catch (NegativeInputException e) {
                System.out.println("Введите положительное число");
                scanner.nextLine();
            } catch (RangeException e) {
                System.out.println("Введите номер из списка");
                scanner.nextLine();
            }
        }

        return actionNum;
    }

    public static void main(String[] args) {

        var scanner = new Scanner(System.in);

        var servicess = new ArrayList<Services>();
        var currentServices = new ArrayList<Services>();

        Lambda summ = current ->{
            int sum = 0;
            for(Services services: current){
                sum += services.getPrice();
            }
            return sum;
        };
        var services0 = new Services("Общий массаж", 75);
        var services1 = new Services("Лечебный массаж", 50);
        var services2 = new Services("Консультация врача дермотолога",20);
        var services3 = new Services("Консультация косметолога",30);
        var services4 = new Services("Маска для лица",10);

        servicess.add(services0);
        servicess.add(services1);
        servicess.add(services2);
        servicess.add(services3);
        servicess.add(services4);

        int actionNum;

        do {

            Menu.actionMenu();

            actionNum = getIntInput(scanner,7);

            int secondActionNum;

            switch (actionNum) {

                case 1 -> {

                    for(int i=0; i < servicess.size(); i++){
                        System.out.println(i + 1 + ". " + servicess.get(i).getName() + " " + servicess.get(i).getPrice()+"p.");
                    }
                    System.out.println("0. Вернуться назад");

                    int choice;

                    choice = getIntInput(scanner,servicess.size());

                    if (choice == 0) break;

                    Menu.timingMenu();

                    secondActionNum=getIntInput(scanner,3);
                    switch (secondActionNum){
                        case 1 -> servicess.get(choice-1).setTime("Утро");

                        case 2 -> servicess.get(choice-1).setTime("День");

                        case 3 -> servicess.get(choice-1).setTime("Вечер");
                    }

                    currentServices.add(servicess.get(choice-1));

                }
                case 2 ->{
                    if(currentServices.size() == 0){
                        System.out.println("Ваш список услуг пуст");
                    }
                    else {
                    for(int i = 0; i < currentServices.size(); i++){
                        System.out.println(i + 1 + ". " + currentServices.get(i).getName() + " " + currentServices.get(i).getPrice() + "p. " + currentServices.get(i).getTime());
                        }
                    }
                }
                case 3 ->{
                    if(currentServices.size() == 0){
                        System.out.println("Ваш список услуг пуст");
                    }
                    else {
                        System.out.println(summ.sum(currentServices) + "р.");
                    }
                }
                case  4 ->{
                    try {
                        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("services.bin"));
                        oos.writeObject(currentServices);
                        oos.close();
                        System.out.println("Данные успешно сохранены");
                    } catch (IOException e) {
                        System.out.println("Файл не найден");
                    }
                }
                case 5 ->{
                    try {
                        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("services.bin"));
                        currentServices = (ArrayList<Services>) ois.readObject();
                        System.out.println("Данные успешно загружены");
                        ois.close();
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("Файл не найден");
                    }
                }
                case 6 ->{
                   Thread increaseTread = new Thread(() -> {
                       servicess.sort(Comparator.comparingDouble(Services::getPrice));//сортируем по 1 полю
                       for (Services ser: servicess){
                           System.out.println(ser.getName() + " " + ser.getPrice() + "p");
                       }
                   });
                   increaseTread.start();
                    try {
                        increaseTread.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 7 ->{
                    Thread decreasingTread = new Thread(() -> {
                        servicess.sort(Comparator.comparingDouble(Services::getPrice));
                        for (int i  = servicess.size() - 1; i >= 0; i--){
                            System.out.println(servicess.get(i).getName() + " " + servicess.get(i).getPrice() + "p");
                        }
                    });
                    decreasingTread.start();
                    try {
                        decreasingTread.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }while (actionNum != 0);
    }
}