package baseball;

import static camp.nextstep.edu.missionutils.Console.readLine;
import static camp.nextstep.edu.missionutils.Randoms.pickNumberInRange;

import java.util.ArrayList;
import java.util.List;

public class Application {
    private static final boolean DEFAULT_STATE = false;
    private static final String GAMECLEAR_MESSAGE = "3개의 숫자를 모두 맞히셨습니다! 게임 종료";
    private static final String GAMERESTART_MESSAGE = "게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.";

    public static void main(String[] args) {
        // TODO: 프로그램 구현

        boolean isRestart = false;

        do {
            // 1. 세 자리 수 만들기

            /*
                # 하나의 수를 고르는 방법.
                pickNumberInRange(1,9) => 1 ~ 9 사이의 숫자를 고름
            */

            // computer : 랜덤으로 생성한 세 자리 수를 저장할 list
            List<Integer> computer = new ArrayList<>();
            while (computer.size() < 3) {
                int randomNumber = pickNumberInRange(1, 9);
                if (!computer.contains(randomNumber)) {
                    computer.add(randomNumber);
                }
            }

            System.err.println("computer: " + computer.get(0) + " " + computer.get(1) + " " + computer.get(2));

            // 2. 숫자 야구 게임 진행하기
            System.out.println("숫자 야구 게임을 시작합니다.");

            // 정답 여부를 확인하는 변수
            boolean isGameClear = DEFAULT_STATE;

            while (!isGameClear) {
                System.out.print("숫자를 입력해주세요 : ");

                /*
                TODO: 사용자 입력 예외처리
                - 1) 숫자입니까?
                    yes : inputNumber에 문자열을 정수형으로 변환해서 넣기
                - 2) 3자리입니까?
                    yes : player 배열에 자릿수 분해해서 하나씩 넣기
                - 3) 서로 다른 숫자로 이루어졌습니까?
                    yes : 야구 게임 처리를 시작합니다!
                => 하나라도 NO 라면 IllegalArgumentException 예외를 발생시키자!
                */

                // 플레이어가 입력한 문자열
                String inputString = readLine();

                // 입력 예외처리 - 플레이어가 세 자리 서로 다른 숫자로 구성된 정수형 숫자를 입력했는지 확인
                if (!isNumber(inputString)) {
                    throw new IllegalArgumentException();
                } else if (!isThreeDigitNumber(inputString)) {
                    throw new IllegalArgumentException();
                } else if (!isDifferentDigits(inputString)) {
                    throw new IllegalArgumentException();
                }

                int inputNumber = Integer.parseInt(inputString); // 입력 받은 세 자리 수
                int[] player = new int[3]; // 입력 받은 세 자리 수를 자릿수 별로 담는 array

                player[0] = inputNumber / 100;
                player[1] = (inputNumber - (inputNumber / 100) * 100) / 10;
                player[2] = inputNumber % 10;

                System.err.println("inputNumber: " + inputNumber);
                System.err.println("player: " + player[0] + " " + player[1] + " " + player[2]);

                /*
                    # 각 자릿수를 구하는 방법.
                    백의 자릿수: inputNumber / 100;
                    십의 자릿수: (inputNumber - (inputNumber / 100) * 100) / 10;
                    일의 자릿수: inputNumber % 10;
                */

                // TODO: 스트라이크, 볼, 낫싱의 여부를 확인
                // 정답일 때, isGameClear = true;

                int strikeCount = 0;
                int ballCount = 0;

                // 스트라이크, 볼의 개수 세기
                for (int i = 0; i < player.length; i++) {
                    if (computer.get(i) == player[i]) {
                        strikeCount++;
                    } else if (computer.contains(player[i])) {
                        ballCount++;
                    }
                }

                // 스트라이크, 볼의 개수에 따른 결과 출력
                if (strikeCount == 0 && ballCount == 0) {
                    System.out.println("낫싱");
                } else if (ballCount > 0 && strikeCount > 0) {
                    System.out.println(ballCount + "볼 " + strikeCount + "스트라이크");
                } else if (strikeCount > 0) {
                    System.out.println(strikeCount + "스트라이크");
                } else {
                    System.out.println(ballCount + "볼");
                }
                // 만약 strikeCount == 3 이면, 모두 정답이므로 정답 처리
                if (strikeCount == 3) {
                    isGameClear = true;
                    System.out.println(GAMECLEAR_MESSAGE);
                }
            }

            // 게임 종료 후, 재시작 여부 확인 메시지 출력
            System.out.println(GAMERESTART_MESSAGE);
            String choiceString = readLine();

            // 입력 예외처리
            if (!isNumber(choiceString)) {
                throw new IllegalArgumentException();
            }

            int choice = Integer.parseInt(choiceString);

            if (choice == 1) {
                // 재시작
                System.err.println("1번 - 재시작!");
                isRestart = true;
            } else if (choice == 2) {
                // 게임 종료
                System.err.println("2번 - 종료!");
                isRestart = false;
            }
        } while (isRestart);

    }

    // 입력 받은 값이 정수형인지 판별하는 메소드
    public static boolean isNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }

    // 입력 받은 값이 세 자리 수인지 판별하는 메소드
    public static boolean isThreeDigitNumber(String input) {
        int number = Integer.parseInt(input);
        return (number / 100) >= 1 && (number / 1000) == 0;
    }

    // 입력 받은 값이 서로 다른 수로 구성되었는지 판별하는 메소드
    public static boolean isDifferentDigits(String input) {
        return input.charAt(0) != input.charAt(1)
                && input.charAt(1) != input.charAt(2)
                && input.charAt(0) != input.charAt(2);
    }
}
