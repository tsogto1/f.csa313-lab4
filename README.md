# Лаборатори 4 — JUnit Unit Testing

**Оюутны код:** B232270072
**Оюутны нэр:** Х.Цогтбаяр

## 1. Лабораторийн зорилго

Энэ лабораторийн ажлаар Java програмд JUnit 5 ашиглан нэгжийн тест бичиж, энгийн болон хязгаарын утгуудыг шалгав. Мөн буруу оролтын үед `IllegalArgumentException` зөв шидэгдэж байгаа эсэхийг тестэлсэн.

Тестлэх програм нь тухайн хичээлийн үнэлгээний бүтцээр оюутны нийт оноо болон үсгэн дүнг тооцно.

## 2. GradeCalculator

`GradeCalculator` класс нь хоёр үндсэн методтой.

### `letterGrade(double score)`

Нийт онооноос үсгэн дүнг дараах байдлаар тодорхойлно.

* 90–100 → A
* 80–89.99 → B
* 70–79.99 → C
* 60–69.99 → D
* 0–59.99 → F

Оноо 0–100 хязгаараас гарвал `IllegalArgumentException` шиднэ.

### `totalScore(...)`

Нийт оноог дараах бүтэцтэйгээр тооцно.

| Үнэлгээ         | Дээд оноо |
| --------------- | --------: |
| Ирц             |        10 |
| Лаб + бие даалт |        40 |
| Сорил 1         |        10 |
| Сорил 2         |        10 |
| Шалгалт         |        30 |
| **Нийт**        |   **100** |

Аль нэг оноо сөрөг эсвэл өөрийн дээд хязгаараас хэтэрсэн тохиолдолд `IllegalArgumentException` шиднэ.

## 3. JUnit тестүүд

`GradeCalculatorTest` класст нийт 9 тестийн метод бичсэн.

Тестүүдэд дараах зүйлсийг шалгасан.

* Ердийн утгууд: `95→A`, `85→B`, `75→C`, `65→D`, `30→F`
* Хязгаарын утгууд: `90`, `89.99`, `80`, `70`, `60`, `59.99`, `0`, `100`
* Буруу оноо: `-1`, `-0.01`, `100.01`, `101`
* `totalScore`-ийн зөв нийлбэр
* Бүх оноо дээд хэмжээндээ байх үед `100` гарах
* Сөрөг ирц (`att = -5`)
* Лабораторийн оноо дээд хэмжээнээс хэтрэх (`lab = 41`)
* Бусад оноонууд дээд хязгаараас хэтрэх тохиолдол

Тестүүдийг Arrange–Act–Assert бүтэцтэй бичиж, `@DisplayName` ашиглан ойлгомжтой Монгол нэр өгсөн.

## 4. Parameterized Test

Олон ижил төрлийн утгыг нэг тестээр шалгахын тулд JUnit 5-ийн `@ParameterizedTest`, `@CsvSource`, `@ValueSource` ашигласан.

`letterGrade`-ийн ердийн болон хязгаарын утгуудыг parameterized тестээр шалгасан. Мөн `totalScore`-ийн хэд хэдэн өөр оролтыг parameterized тестээр шалгасан.

## 5. Тестийн үр дүн

Бүх тестийг дараах командаар ажиллуулсан.

```bash
mkdir -p results && mvn test 2>&1 | tee results/mvn-test.txt
```

Эцсийн зөв хувилбар дээр:

```text
Tests run: 25, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

гарсан.

Тестийн гаралтыг `results/mvn-test.txt` файлд хадгалсан.

## 6. Mutation Testing

Тестүүд хязгаарын нөхцөлийг зөв шалгаж байгаа эсэхийг үзэхийн тулд `letterGrade` методын:

```java
score >= 90
```

нөхцөлийг зориуд:

```java
score > 90
```

болгож өөрчилсөн.

Дараа нь:

```bash
mvn test 2>&1 | tee results/mvn-test-mutant.txt
```

командыг ажиллуулсан.

Mutation хийсэн үед дараах үр дүн гарсан.

```text
Tests run: 25, Failures: 2, Errors: 0, Skipped: 0
BUILD FAILURE
```

Унасан тестүүд:

* `ninetyIsExactlyA`
* `letterGradeBoundaries[2]`

Хоёр тест хоёулаа 90 оноо `A` байх ёстой гэсэн хязгаарын нөхцөлийг шалгадаг. Mutation хийсний дараа 90 оноо `B` болж, эдгээр тестүүд унасан. Ингэснээр тестүүд маань 90 гэсэн boundary value-ийн өөрчлөлтийг илрүүлж байгааг шалгасан.

Mutation testing-ийн гаралтыг:

```text
results/mvn-test-mutant.txt
```

файлд хадгалсан.

Дараа нь `score >= 90` нөхцөлийг буцааж засаж, бүх тестийг дахин ажиллуулсан.

## 7. Үр дүн

Энэ лабораторийн ажлаар JUnit 5 ашиглан `GradeCalculator` классын үндсэн үйлдлүүд, хязгаарын утгууд болон буруу оролтуудыг шалгасан. Parameterized test ашигласнаар ижил бүтэцтэй олон оролтыг давталт багатайгаар тестлэх боломжтой болсон. Мөн mutation testing хийж, 90 онооны boundary-г шалгасан тестүүд зориудын өөрчлөлтийг илрүүлж байгааг баталгаажуулсан.

## 8. Хөгжүүлэлтийн орчин (Development Environment)

* **Үйлдлийн систем:** Debian Linux (x86_64)
* **Java:** OpenJDK 21
* **Build Tool:** Apache Maven 3.9.9

### Командын гаралт (System Output)

tsogto@debian:~/Documents/gobi bagsh/lab4/lab04-junit$ mvn -version
Apache Maven 3.9.9
Maven home: /usr/share/maven
Java version: 21.0.12.1, vendor: Debian, runtime: /usr/lib/jvm/java-21-openjdk-amd64
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "6.12.107+deb13-amd64", arch: "amd64", family: "unix"
tsogto@debian:~/Documents/gobi bagsh/lab4/lab04-junit$ java -version
openjdk version "21.0.12.1" 2026-08-18
OpenJDK Runtime Environment (build 21.0.12.1+1-1-deb13u1-Debian)
OpenJDK 64-Bit Server VM (build 21.0.12.1+1-1-deb13u1-Debian, mixed mode, sharing)