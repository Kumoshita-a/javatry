/*
 * Copyright 2019-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.javatry.basic;

import org.docksidestage.bizfw.basic.buyticket.Ticket;
import org.docksidestage.bizfw.basic.buyticket.TicketBooth;
import org.docksidestage.bizfw.basic.objanimal.Animal;
import org.docksidestage.bizfw.basic.objanimal.BarkedSound;
import org.docksidestage.bizfw.basic.objanimal.Cat;
import org.docksidestage.bizfw.basic.objanimal.Dog;
import org.docksidestage.bizfw.basic.objanimal.Elephant;
import org.docksidestage.bizfw.basic.objanimal.Zombie;
import org.docksidestage.bizfw.basic.objanimal.loud.AlarmClock;
import org.docksidestage.bizfw.basic.objanimal.loud.Loudable;
import org.docksidestage.bizfw.basic.objanimal.runner.FastRunner;
import org.docksidestage.bizfw.basic.objanimal.swimmer.Swimmable;
import org.docksidestage.javatry.basic.st6.dbms.St6Database;
import org.docksidestage.javatry.basic.st6.dbms.St6MySql;
import org.docksidestage.javatry.basic.st6.dbms.St6PostgreSql;
import org.docksidestage.javatry.basic.st6.os.St6MacOperationSystem;
import org.docksidestage.javatry.basic.st6.os.St6OldWindowsOperationSystem;
import org.docksidestage.javatry.basic.st6.os.St6OperationSystem;
import org.docksidestage.javatry.basic.st6.os.St6WindowsOperationSystem;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of object-oriented. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author Kumoshita-a
 */
public class Step06ObjectOrientedTest extends PlainTestCase {

    // ===================================================================================
    //                                                                        About Object
    //                                                                        ============
    // -----------------------------------------------------
    //                                        Against Object
    //                                        --------------
    /**
     * Fix several mistakes (except simulation) in buying one-day passport and in-park process. <br>
     * (OneDayPassportを買って InPark する処理の中で、(simulationを除いて)間違いがいくつかあるので修正しましょう)
     */
    public void test_objectOriented_aboutObject_againstObject() {
        // TODO done kumo (1on1の後に気付いた) 間違い、あと2つあります。結構単純な間違いです by jflute (2025/10/14)
        //
        // [ticket booth info]
        //
        // simulation: actually these variables should be more wide scope
        int oneDayPrice = 7400;
        int quantity = 10;
        Integer salesProceeds = null;

        //
        // [buy one-day passport]
        //
        // simulation: actually this money should be from customer
        int handedMoney = 10000;
        if (quantity <= 0) {
            throw new IllegalStateException("Sold out");
        }
        if (handedMoney < oneDayPrice) {
            throw new IllegalStateException("Short money: handedMoney=" + handedMoney);
        }
        --quantity; // 正しく購入処理ができたあとに在庫を減らすように修正
        // salesProceeds = handedMoney; // 計上するお金がチケットの値段ではなく、持っているお金になっている。
        salesProceeds = oneDayPrice;

        //
        // [ticket info]
        //
        // simulation: actually these variables should be more wide scope
        // int displayPrice = quantity; // 値段が入るところに在庫が入っている
        // そもそもdisplayPriceって何を示すのか？→showYourTicket
        int displayPrice = oneDayPrice;
        boolean alreadyIn = false;

        // other processes here...
        // ...
        // ...

        //
        // [do in park now!!!]
        //
        // simulation: actually this process should be called by other trigger
        if (alreadyIn) {
            throw new IllegalStateException("Already in park by this ticket: displayPrice=" + displayPrice); // displayPriceじゃなくてquantityが入っていたので修正
        }
        alreadyIn = true;

        // #1on1: ベタ書きとオブジェクト使ったやり方の差の話。
        // メソッドを作る側の意識:
        // o 極力、こういうメソッド作らないように努力
        // o オブジェクトを導入したり... (オブジェクトの粒度は色々)
        // o 引数の順序を工夫したり... (100%の技ではないが)
        // o javadocで引数を強調したり... (100%の技ではないが)
        //
        // メソッドを呼ぶ側の意識:
        // o 5秒指差し確認 ☆技術者としてのスキルというよりも開発者としてのスキル (ものづくりスキル)
        // o 5秒指差しをやるとき、やらないときがある: 仕事も早くて安定してる人は、やった方がいいときやる
        // o 経験で、「ここ危ないな」って場面をよく知っているかどうか？意識しているかどうか？
        // o その積み重ねをしている人としていない人
        // o あと、自分が間違えやすいポイントを知ってる人、「ここ自分は危ないな」
        //
        // [final process]
        //
        // saveBuyingHistory(quantity, displayPrice, salesProceeds, alreadyIn); // メソッドの引数はint quantity, Integer salesProceeds, int displayPrice, boolean alreadyInだが、代入の順序が違う
        saveBuyingHistory(quantity, salesProceeds, displayPrice, alreadyIn);
    }

    private void saveBuyingHistory(int quantity, Integer salesProceeds, int displayPrice, boolean alreadyIn) {
        if (alreadyIn) {
            // simulation: only logging here (normally e.g. DB insert)
            // showTicketBooth(displayPrice, salesProceeds); // メソッドの引数はint quantity, Integer salesProceedsだが、代入する値がおかしい
            // showYourTicket(quantity, alreadyIn); // メソッドの引数はint displayPrice, boolean alreadyInだが、代入する値がおかしい
            // そもそも引数のセットから考えると、呼び出すメソッドが違う
            showYourTicket(displayPrice, alreadyIn);
            showTicketBooth(quantity, salesProceeds);
        }
    }

    private void showTicketBooth(int quantity, Integer salesProceeds) {
        log("Ticket Booth: quantity={}, salesProceeds={}", quantity, salesProceeds);
    }

    private void showYourTicket(int displayPrice, boolean alreadyIn) {
        log("Ticket: displayPrice={}, alreadyIn={}", displayPrice, alreadyIn);
    }

    // -----------------------------------------------------
    //                                          Using Object
    //                                          ------------
    /**
     * Read (analyze) this code and compare with the previous test method, and think "what is object?". <br>
     * (このコードを読んで(分析して)、一つ前のテストメソッドと比べて、「オブジェクトとは何か？」を考えてみましょう)
     */
    public void test_objectOriented_aboutObject_usingObject() {
        //
        // [ticket booth info]
        //
        TicketBooth booth = new TicketBooth();

        // *booth has these properties:
        //int oneDayPrice = 7400;
        //int quantity = 10;
        //Integer salesProceeds = null;

        //
        // [buy one-day passport]
        //
        // if step05 has been finished, you can use this code by jflute (2019/06/15)
        //Ticket ticket = booth.buyOneDayPassport(10000);
        booth.buyOneDayPassport(10000); // as temporary, remove if you finished step05
        Ticket ticket = new Ticket(7400); // also here

        // *buyOneDayPassport() has this process:
        //if (quantity <= 0) {
        //    throw new TicketSoldOutException("Sold out");
        //}
        //if (handedMoney < oneDayPrice) {
        //    throw new TicketShortMoneyException("Short money: handedMoney=" + handedMoney);
        //}
        //--quantity;
        //salesProceeds = handedMoney;

        // *ticket has these properties:
        //int displayPrice = oneDayPrice;
        //boolean alreadyIn = false;

        // other processes here...
        // ...
        // ...

        //
        // [do in park now!!!]
        //
        ticket.doInPark();

        // *doInPark() has this process:
        //if (alreadyIn) {
        //    throw new IllegalStateException("Already in park by this ticket: displayPrice=" + displayPrice);
        //}
        //alreadyIn = true;

        //
        // [final process]
        //
        saveBuyingHistory(booth, ticket);

        // オブジェクトとは何か？
        // - ドメイン上の意味と、それを一貫した正しい状態に保つ振る舞いをひとまとまりにしたもの
        // - 外部から同様に呼び出せるようにデータとメソッドをまとめたもの (処理に変更を加えた際に他の箇所も同様に変更を反映できるようにしたもの：保守性を高める)

        // #1on1: "ドメイン上の意味" をいかに見出すかがポイント。
        // トップダウンアプローチとボトムアップアプローチ
        // DB設計でも同じ
        // 両方をうまく使っていくことが大事
        // javatryのstep5とstep6のコンセプト
        // オブジェクトが綺麗に見いだせてこその三大原則
    }

    private void saveBuyingHistory(TicketBooth booth, Ticket ticket) {
        if (ticket.isAlreadyIn()) {
            // only logging here (normally e.g. DB insert)
            doShowTicketBooth(booth);
            doShowYourTicket(ticket);
        }
    }

    private void doShowTicketBooth(TicketBooth booth) {
        log("Ticket Booth: quantity={}, salesProceeds={}", booth.getQuantity(), booth.getSalesProceeds());
    }

    private void doShowYourTicket(Ticket ticket) {
        log("Your Ticket: displayPrice={}, alreadyIn={}", ticket.getDisplayPrice(), ticket.isAlreadyIn());
    }

    // write your memo here:
    // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
    // what is object?
    //
    // _/_/_/_/_/_/_/_/_/_/

    // ===================================================================================
    //                                                              Polymorphism Beginning
    //                                                              ======================
    /**
     * What string is sea and land variable at the method end? <br>
     * (メソッド終了時の変数 sea, land の中身は？)
     */
    public void test_objectOriented_polymorphism_1st_concreteOnly() {
        Dog dog = new Dog();
        BarkedSound sound = dog.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => wan
        int land = dog.getHitPoint();
        log(land); // your answer? => 7
        // breatheIn(), prepareAbdominalMuscle(), doBark(barkWord) で3回HitPointが減っている
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_2nd_asAbstract() {
        Animal animal = new Dog(); // DogはAnimalクラスを継承しているので型エラーは起きない
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord(); // animalクラスであるが、Dogでインスタンスが作られているので、"wan"が入る
        log(sea); // your answer? => wan
        int land = animal.getHitPoint();
        log(land); // your answer? => 7
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_3rd_fromMethod() {
        Animal animal = createAnyAnimal();
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => wan
        int land = animal.getHitPoint();
        log(land); // your answer? => 7
        // test_objectOriented_polymorphism_2nd_asAbstractと内容は同じ、インスタンスの生成を別メソッドに抽出しただけ→メリットは？
        // #1on1: test_メソッド側が、Dogに依存をしていない。DogをCatに変える時、test_を1文字を修正しない。
    }

    private Animal createAnyAnimal() {
        return new Dog();
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_4th_toMethod() {
        Dog dog = new Dog();
        doAnimalSeaLand_for_4th(dog);
    }

    private void doAnimalSeaLand_for_4th(Animal animal) {
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => wan
        int land = animal.getHitPoint();
        log(land); // your answer? => 7
        // インスタンス生成以外の一連の処理をメソッドにまとめた→再利用しやすくなった
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_5th_overrideWithSuper() {
        Animal animal = new Cat(); // Catクラスのインスタンスが生成される
        BarkedSound sound = animal.bark();
            // breatheIn(); 9（奇数なので一回だけダメージ）
            // prepareAbdominalMuscle(); 8 偶数なので2回ダメージ）→ 7
            // BarkedSound barkedSound = doBark(barkWord); 6（偶数なので2回ダメージ）→ 5
        String sea = sound.getBarkWord();
        log(sea); // your answer? => nya-
        int land = animal.getHitPoint();
        // TODO done kumo CatなのでdownHitPointするので... (書くところ間違えたみたいですね) by jflute (2025/10/14)
        log(land); // your answer? => 5
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_6th_overriddenWithoutSuper() {
        Animal animal = new Zombie();
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => uooo
        int land = animal.getHitPoint();
        // TODO done kumo Zombieの場合は、またちょっと違う結果になる (書くところ間違えたようで) by jflute (2025/10/14)
        log(land); // your answer? => -1
            // getInitialHitPointより、初期値は-1
            // downHitPointは何も書かれてないので、hitPointは初期値のまま
    }

    /**
     * What is happy if you can assign Dog or Cat instance to Animal variable? <br>
     * (Animal型の変数に、DogやCatなどのインスタンスを代入できると何が嬉しいのでしょう？)
     */
    public void test_objectOriented_polymorphism_7th_whatishappy() {
        // write your memo here:
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // what is happy?
        // 同じメソッドを呼び出すことが可能になる（共通したメソッドを同じように利用できる（ただし、中身はオーバーライドで自由に変更可能））
        // _/_/_/_/_/_/_/_/_/_/
        // #1on1: 日常の世界でも、ポリモーフィズムいっぱいある話
    }
    
    // TODO jflute 次回1on1ここから (2025/10/14)
    // ===================================================================================
    //                                                              Polymorphism Interface
    //                                                              ======================
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_interface_dispatch() {
        Loudable loudable = new Zombie();
        String sea = loudable.soundLoudly();
            // bark().getBarkWord();なので、Zombieのbark()が呼ばれる
        log(sea); // your answer? => uooo
        String land = ((Zombie) loudable).bark().getBarkWord();
            // Loudable型をZombie型にキャストしてからbark()を呼ぶので、やはりZombieのbark()が呼ばれる
        log(land); // your answer? => uooo
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_interface_hierarchy() {
        Loudable loudable = new AlarmClock();
        String sea = loudable.soundLoudly();
        log(sea); // your answer? => jiri jiri jiri---
        boolean land = loudable instanceof Animal;
            // implements Loudable なのでAnimalのインスタンスではない
        log(land); // your answer? => false
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_interface_partImpl() {
        Animal seaAnimal = new Cat();
        Animal landAnimal = new Zombie();
        boolean sea = seaAnimal instanceof FastRunner;
            // public class Cat extends Animal implements FastRunner なので
        log(sea); // your answer? => true
        boolean land = landAnimal instanceof FastRunner;
            // public class Zombie extends Animal なので
        log(land); // your answer? => false
    }

    /**
     * Make Dog class implement FastRunner interface. (the method implementation is same as Cat class) <br>
     * (DogもFastRunnerインターフェースをimplementsしてみましょう (メソッドの実装はCatと同じで))
     */
    public void test_objectOriented_polymorphism_interface_runnerImpl() {
        // your confirmation code here
        Animal animal = new Dog();
        boolean sea = animal instanceof FastRunner;
        log(sea); // your answer? => true
    }

    /**
     * What is difference as concept between abstract class and interface? <br>
     * (抽象クラスとインターフェースの概念的な違いはなんでしょう？)
     */
    public void test_objectOriented_polymorphism_interface_whatisdifference() {
        // write your memo here:
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // what is difference?
        // 抽象クラス：「is-a関係」同じ種類のものの共通部分を抽出、継承は1つだけ
        // インターフェース：「can-do関係」できることの契約を定義、複数実装可能
        // _/_/_/_/_/_/_/_/_/_/
    }

    // ===================================================================================
    //                                                                 Polymorphism Making
    //                                                                 ===================
    /**
     * Make concrete class of Animal, which is not FastRunner, in "objanimal" package. (implementation is as you like) <br>
     * (FastRunnerではないAnimalクラスのコンクリートクラスをobjanimalパッケージに作成しましょう (実装はお好きなように))
     */
    public void test_objectOriented_polymorphism_makeConcrete() {
        // your confirmation code here
        Animal elephant = new Elephant();
        BarkedSound sound = elephant.bark();
        String barkWord = sound.getBarkWord();
        log(barkWord); // "pao"
        int hitPoint = elephant.getHitPoint();
        log(hitPoint); // 12 (初期15 - bark()で3回downHitPoint()が呼ばれる)
    }

    /**
     * Make interface implemented by part of Animal concrete class in new package under "objanimal" package. (implementation is as you like) <br>
     * (Animalクラスの一部のコンクリートクラスだけがimplementsするインターフェースをobjanimal配下の新しいパッケージに作成しましょう (実装はお好きなように))
     */
    public void test_objectOriented_polymorphism_makeInterface() {
        // your confirmation code here
        Animal dog = new Dog();
        Animal cat = new Cat();
        Animal elephant = new Elephant();
        
        // DogとCatはSwimmableを実装している
        boolean dogCanSwim = dog instanceof Swimmable;
        boolean catCanSwim = cat instanceof Swimmable;
        boolean elephantCanSwim = elephant instanceof Swimmable;
        
        log("Dog can swim: " + dogCanSwim); // true
        log("Cat can swim: " + catCanSwim); // true
        log("Elephant can swim: " + elephantCanSwim); // false
        
        // Swimmableインターフェースとして扱う
        if (dog instanceof Swimmable) {
            Swimmable swimmableDog = (Swimmable) dog;
            swimmableDog.swim(); // 泳ぐ
        }
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Extract St6MySql, St6PostgreSql (basic.st6.dbms)'s process to abstract class (as super class and sub-class) <br>
     * (St6MySql, St6PostgreSql (basic.st6.dbms) から抽象クラスを抽出してみましょう (スーパークラスとサブクラスの関係に))
     */
    public void test_objectOriented_writing_generalization_extractToAbstract() {
        // your confirmation code here
        St6Database mysql = new St6MySql("jdbc:mysql://localhost:3306/mydb", "root", "password");
        St6Database postgresql = new St6PostgreSql("jdbc:postgresql://localhost:5432/mydb", "postgres", "password");
        
        // MySQLのページングクエリ: "limit offset, pageSize"形式
        String mysqlPaging = mysql.getPagingQuery(10, 3);
        log("MySQL paging query: " + mysqlPaging); // "limit 20, 10"
        
        // PostgreSQLのページングクエリ: "offset offset limit pageSize"形式
        String postgresqlPaging = postgresql.getPagingQuery(10, 3);
        log("PostgreSQL paging query: " + postgresqlPaging); // "offset 20 limit 10"
        
        // 共通の属性にアクセス
        log("MySQL URL: " + mysql.getUrl());
        log("PostgreSQL URL: " + postgresql.getUrl());
        
        // 抽象クラス型として扱える
        St6Database[] databases = {mysql, postgresql};
        for (St6Database db : databases) {
            String pagingQuery = db.getPagingQuery(5, 2);
            log("Database paging: " + pagingQuery);
            // Database paging: limit 20, 10 // MySQL
            // Database paging: offset 10 limit 5 // PostgreSQL
            // の順で出力される
        }
    }

    /**
     * Extract St6OperationSystem (basic.st6.os)'s process to concrete classes (as super class and sub-class) <br>
     * (St6OperationSystem (basic.st6.os) からコンクリートクラスを抽出してみましょう (スーパークラスとサブクラスの関係に))
     */
    public void test_objectOriented_writing_specialization_extractToConcrete() {
        // your confirmation code here
        St6OperationSystem windows = new St6WindowsOperationSystem("Alice");
        St6OperationSystem mac = new St6MacOperationSystem("Bob");
        St6OperationSystem oldWindows = new St6OldWindowsOperationSystem("Charlie");
        
        // Windowsのファイルパス構築
        String windowsPath = windows.buildUserResourcePath("Documents/photo.jpg");
        log("Windows path: " + windowsPath); // "C:\Users\Alice\Documents\photo.jpg"
        
        // Macのファイルパス構築
        String macPath = mac.buildUserResourcePath("Documents/photo.jpg");
        log("Mac path: " + macPath); // "/Users/Bob/Documents/photo.jpg"
        
        // Old Windowsのファイルパス構築
        String oldWindowsPath = oldWindows.buildUserResourcePath("Documents/photo.jpg");
        log("Old Windows path: " + oldWindowsPath); // "C:\Documents and Settings\Charlie\Documents\photo.jpg"
        
        // 共通の属性にアクセス
        log("Windows OS type: " + windows.getOsType()); // "Windows"
        log("Mac OS type: " + mac.getOsType()); // "Mac"
        
        // 抽象クラス型として扱える
        St6OperationSystem[] systems = {windows, mac, oldWindows};
        for (St6OperationSystem os : systems) {
            String path = os.buildUserResourcePath("config.txt");
            log(os.getOsType() + " config path: " + path);
                // Windows config path: \Users\Alice\config.txt
                // Mac config path: /Users/Bob/config.txt
                // OldWindows config path: \Documents and Settings\Charlie\config.txt
                // の順で出力される
        }
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * Extract Animal's bark() process as BarkingProcess class to also avoid large abstract class. <br>
     * (抽象クラス肥大化を抑制するためにも、Animalのbark()のプロセス(処理)をBarkingProcessクラスとして切り出しましょう)
     */
    public void test_objectOriented_writing_withDelegation() {
        // your confirmation code here
    }

    /**
     * Put barking-related classes, such as BarkingProcess and BarkedSound, into sub-package. <br>
     * (BarkingProcessやBarkedSoundなど、barking関連のクラスをサブパッケージにまとめましょう)
     * <pre>
     * e.g.
     *  objanimal
     *   |-barking
     *   |  |-BarkedSound.java
     *   |  |-BarkingProcess.java
     *   |-loud
     *   |-runner
     *   |-Animal.java
     *   |-Cat.java
     *   |-Dog.java
     *   |-...
     * </pre>
     */
    public void test_objectOriented_writing_withPackageRefactoring() {
        // your confirmation code here
    }

    /**
     * Is Zombie correct as sub-class of Animal? Analyze it in thirty seconds. (thinking only) <br>
     * (ゾンビは動物クラスのサブクラスとして適切でしょうか？30秒だけ考えてみましょう (考えるだけでOK))
     */
    public void test_objectOriented_zoo() {
        // write your memo here:
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // is it correct?
        //
        // _/_/_/_/_/_/_/_/_/_/
    }
}
