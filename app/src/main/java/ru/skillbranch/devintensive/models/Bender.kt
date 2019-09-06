package ru.skillbranch.devintensive.models

class Bender (var status:Status = Status.NORMAL, var question:Question = Question.NAME) {

    fun askQuestion() = when (question){
                Question.NAME -> Question.NAME.question
                Question.PROFESSION -> Question.PROFESSION.question
                Question.MATERIAL -> Question.MATERIAL.question
                Question.BDAY -> Question.BDAY.question
                Question.SERIAL -> Question.SERIAL.question
                Question.IDLE -> Question.IDLE.question
    }

    fun listerAnswer(answer:String) : Pair<String, Triple<Int, Int, Int>>{
        val (isValid,message) = question.validate(answer)
        if (isValid)
            if (question.answers.contains(answer.toLowerCase())){
                question = question.nextQuestion()
                return "Отлично - ты справился\n${question.question}" to status.color
            }
            else{
                status = status.nextStatus()
                return if (status == Status.NORMAL){
                    //reset
                    question = Question.NAME
                    "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
                } else "Это неправильный ответ\n${question.question}" to status.color
            }
        else
            return "${message}\n${question.question}" to status.color
    }

    enum class Status (val color : Triple<Int, Int, Int> ) {
        NORMAL(Triple(255, 255, 255)) ,
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0)) ;

        fun nextStatus() = if (ordinal < values().lastIndex) values()[ordinal+1] else values()[0]
    }

    enum class Question (val question:String, val answers:List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun validate(value: String): Pair<Boolean, String> {
                return if (value.isNullOrEmpty() || value[0].isLowerCase()) false to "Имя должно начинаться с заглавной буквы" else true to "OK"
            }

            override fun nextQuestion(): Question  = PROFESSION
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun validate(value: String): Pair<Boolean, String> {
                return if (value.isNullOrEmpty() || value[0].isUpperCase()) false to "Профессия должна начинаться со строчной буквы" else true to "OK"
            }

            override fun nextQuestion(): Question  = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun validate(value: String): Pair<Boolean, String> {
                for (ch in value){
                    if (ch.isDigit()) return false to "Материал не должен содержать цифр"
                }
                return true to "OK"
            }

            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun validate(value: String): Pair<Boolean, String> {
                for (ch in value){
                    if (!ch.isDigit()) return false to "Год моего рождения должен содержать только цифры"
                }
                return true to "OK"

            }

            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun validate(value: String): Pair<Boolean, String> {

                if (value.length != 7) return false to "Серийный номер содержит только цифры, и их 7"

                for (ch in value){
                    if (!ch.isDigit()) return false to "Серийный номер содержит только цифры, и их 7"
                }

                return true to "OK"
            }

            override fun nextQuestion(): Question  = IDLE
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun validate(value: String): Pair<Boolean, String> {
                return false to "Отлично - ты справился"
            }

            override fun nextQuestion(): Question  = NAME
        };

        abstract fun nextQuestion() : Question

        abstract fun validate(value:String) : Pair<Boolean,String> // <isValid,message>
    }
}