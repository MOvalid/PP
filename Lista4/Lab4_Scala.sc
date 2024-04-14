sealed trait H_Element
case object NewLineChar extends H_Element
case class Heading(number: Int, text: List[String]) extends H_Element
case class Paragraph(text: List[String]) extends H_Element
case class TextArea(text: List[String]) extends H_Element
case class HListElement(inside: List[H_Element]) extends H_Element
case class OrderedList(list: List[H_Element]) extends H_Element
case class UnorderedList(list: List[H_Element]) extends H_Element

sealed trait H_Title
case object Empty extends H_Title
case class Title(text: String) extends H_Title

def build(title: H_Title, list: List[H_Element]): String = {

  def checkNumber(number: Int): Boolean = number >= 1 && number <= 6

  def nestedList(beginTag: String, endTag: String, list: List[H_Element], howMuchTab: Int): String = {
    "\n" + setTabulation(beginTag, howMuchTab) + readHList(list, howMuchTab + 1) + setTabulation(endTag, howMuchTab)
  }

  def readTable(table: List[String], nextTag: String): String = {
    table match
      case List() => ""
      case h :: t => nextTag + h + readTable(t, "<br>")
  }

  def readTitle(title: H_Title): String = {
    title match
      case Empty | Title("")   => ""
      case Title(text)         => "<head>\n\n<title>" + text + "</title>\n\n</head>\n"
  }

  def setTabulation(tag: String, howMuch: Int): String = {
    howMuch match
      case 0 => tag
      case _ => setTabulation("\t" + tag, howMuch - 1)
  }

  def readElement(list: List[H_Element]): String = {
    list match
      case List()                                            => ""

      case NewLineChar :: t                                  => "<br>\n" + readElement(t)

      case Heading(number, text) :: t if checkNumber(number) => "<h" + number + ">" + readTable(text, "") +  "</h" + number + ">\n" + readElement(t)

      case Paragraph(text) :: t                              => "<p>" + readTable(text, "") + "</p>\n" + readElement(t)

      case OrderedList(list) :: t if list.nonEmpty           => "<ol>\n" + readHList(list, 1) + "</ol>\n" + readElement(t)

      case UnorderedList(list) :: t if list.nonEmpty         => "<ul>\n" + readHList(list, 1) + "</ul>\n" + readElement(t)

      case _ :: t                                            => readElement(t)
  }

  def readHList(list: List[H_Element], howMuchTab: Int): String = {
    list match
      case List()                      => ""

      case NewLineChar :: t            => setTabulation("<br>\n", howMuchTab) + readHList(t, howMuchTab)

      case HListElement(inside) :: t   => setTabulation("<li>", howMuchTab) + readHListElement(inside, howMuchTab, true) + "</li>\n" + readHList(t, howMuchTab)

      case elem :: t                   => setTabulation("<li>", howMuchTab) + readHListElement(List(elem), howMuchTab, true) + "</li>\n" + readHList(t, howMuchTab)
  }

  def readHListElement(list: List[H_Element], howMuchTab: Int, onlyTextArea: Boolean): String = {
    list match
      case List()                                            => if onlyTextArea then "" else "\n" + setTabulation("", howMuchTab)

      case NewLineChar :: t                                  => "\n" + setTabulation("<br>", howMuchTab + 1) + readHListElement(t, howMuchTab, false)

      case TextArea(text) :: TextArea(_) :: _                => readTable(text, "") + " " + readHListElement(list.tail, howMuchTab, onlyTextArea)

      case TextArea(text) :: t                               => if onlyTextArea then readTable(text, "") + readHListElement(t, howMuchTab, onlyTextArea)
                                                                else "\n" + setTabulation(readTable(text, ""), howMuchTab+1) + readHListElement(t, howMuchTab, onlyTextArea)

      case Paragraph(text) :: t                              => "\n" + setTabulation("<p>", howMuchTab + 1) + readTable(text, "") + "</p>" + readHListElement(t, howMuchTab, false)

      case Heading(number, text) :: t if checkNumber(number) => "\n" + setTabulation("<h" + number + ">", howMuchTab + 1) + readTable(text, "") + "</h" + number + ">" + readHListElement(t, howMuchTab, false)

      case OrderedList(list) :: t if list.nonEmpty           => nestedList("<ol>\n", "</ol>", list, howMuchTab + 1) + readHListElement(t, howMuchTab, false)

      case UnorderedList(list) :: t if list.nonEmpty         => nestedList("<ul>\n", "</ul>", list, howMuchTab + 1) + readHListElement(t, howMuchTab, false)

      case _ :: t                                            => readHListElement(t, howMuchTab, onlyTextArea)
  }

  list match
    case List() => readTitle(title)
    case _      => "\n<!DOCTYPE html>\n<html>\n" + readTitle(title) + "<body>\n\n" + readElement(list) + "\n</body>\n</html>\n"

}


val milkLactose = List(TextArea(List("With lactose")), TextArea(List("Without lactose")), NewLineChar)

val drinkTypes  = List(TextArea(List("Tea")), TextArea(List("Milk")), TextArea(List("Coffee")))

val milkPercent = List(NewLineChar, TextArea(List("3.2%")), TextArea(List("2.0%")), TextArea(List("0.5%")), NewLineChar)

val milkTypes   = List(HListElement(List(TextArea(List("Whole", "milk")),UnorderedList(milkPercent), UnorderedList(milkLactose))), TextArea(List("Soy milk")), TextArea(List("Almond milk")))

val coffeeTypes  = List(HListElement(List(TextArea(List("With milk")), UnorderedList(milkTypes))), TextArea(List("With sugar")), TextArea(List("With milk and sugar")))

val orderedDrinks = List(TextArea(List("Tea")), TextArea(List("Milk")), HListElement(List(TextArea(List("Coffee")), OrderedList(coffeeTypes))))

val unorderedDrinks = List(TextArea(List("Tea")), TextArea(List("Milk")), HListElement(List(TextArea(List("Coffee")), UnorderedList(coffeeTypes))))

val options = List(TextArea(List("First option")) , TextArea(List("Second option")), TextArea(List("Third option")))

val ListHTML1 = List(NewLineChar, Heading(7, List("Incorrect Heading")), Paragraph(List("First paragraph")), OrderedList(drinkTypes), UnorderedList(drinkTypes))

val ListHTML2 = List(Heading(1,List("First", "Heading")), Paragraph(List("First paragraph")), OrderedList(unorderedDrinks), UnorderedList(orderedDrinks))

val ListHTML3 = List(Heading(7,List("Incorrect Heading")), Paragraph(List("First paragraph")), OrderedList(List(HListElement(List(Heading(3, List("First Heading")),  Paragraph(List("First paragraph")))), HListElement(List(Heading(3, List("Second Heading")),  Paragraph(List("Second paragraph")))),  HListElement(List(Heading(3, List("Third Heading")),  Paragraph(List("Third paragraph")), UnorderedList(options))))))

build(Title("Page Title 1"), ListHTML1)

print(build(Title(""), ListHTML2))

build(Title("Page Title 1"), ListHTML3)

build(Title("Page Title 1"), List(Paragraph(List("PARAGRAPH"))))

build(Empty, List(Paragraph(List("PARAGRAPH"))))

build(Title("Page Title 1"), List(Heading(70, List("Heading 70")), Heading(1, List("Heading 1"))))

