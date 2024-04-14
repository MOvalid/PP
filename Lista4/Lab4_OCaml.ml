OCaml

type h_Element = NewLineChar
 | Heading of int * string list
 | Paragraph of string list
 | TextArea of string list
 | HListElement of h_Element list
 | OrderedList of h_Element list
 | UnorderedList of h_Element list;;
 
type h_Title = Empty | Title of string;;

let build hTitle lst = 

	let checkNumber number = (number >= 1 && number <= 6) in

	let rec setTabulation tag howMuch =
		match howMuch with
		| 0 -> tag
		| _ -> setTabulation ("\t" ^ tag) (howMuch-1)
		
	
	in let rec readTable table nextTag =
		match table with 
		| []     -> ""
		| h :: t -> nextTag ^ h ^ (readTable t "<br>")
		

	in let readTitle title = 
		match title with 
		| Empty | Title("")   -> ""
		| Title(text)         -> "<head>\n\n<title>" ^ text ^ "</title>\n\n</head>\n"


	in let rec readHList hlst howMuchTab =
	
		let nestedList beginTag endTag lst howMuchTab = "\n" ^ (setTabulation beginTag howMuchTab) ^ (readHList lst (howMuchTab+1)) ^ (setTabulation endTag howMuchTab) in

		let rec readHListElement hlst howMuchTab onlyTextArea = 
			match hlst with
			| [] 													 -> if onlyTextArea then "" else "\n" ^ (setTabulation "" howMuchTab)
			
			| NewLineChar :: t									     -> "\n" ^ (setTabulation "<br>" (howMuchTab + 1)) ^ (readHListElement t howMuchTab false) 
			
			| TextArea(text) :: TextArea(_) :: _				     -> (readTable text "") ^ " " ^ (readHListElement (List.tl hlst) howMuchTab onlyTextArea)
			
			| TextArea(text) :: t				   					 -> if onlyTextArea then (readTable text "") ^ (readHListElement t howMuchTab onlyTextArea)	
																		else "\n" ^ (setTabulation (readTable text "") (howMuchTab + 1)) ^ (readHListElement t howMuchTab onlyTextArea)
																		
			| Paragraph(text) :: t                                   -> "\n" ^ (setTabulation "<p>" (howMuchTab + 1)) ^ (readTable text "") ^ "</p>" ^ (readHListElement t howMuchTab false)
			
			| Heading(number, text) :: t when (checkNumber number)   -> "\n" ^ (setTabulation ("<h" ^ (string_of_int number) ^ ">") (howMuchTab + 1)) ^ (readTable text "") ^ "</h" ^ (string_of_int number) ^ ">" ^ (readHListElement t howMuchTab false)
			
			| OrderedList(olist) :: t    when olist != []            -> (nestedList "<ol>\n" "</ol>" olist (howMuchTab + 1)) ^ (readHListElement t howMuchTab false)
			
			| UnorderedList(ulist) :: t  when ulist != []            -> (nestedList "<ul>\n" "</ul>" ulist (howMuchTab + 1)) ^ (readHListElement t howMuchTab false)
			
			| _ :: t                                                 -> readHListElement t howMuchTab onlyTextArea
		
		in match hlst with
		| [] 							 -> ""
		
		| NewLineChar :: t 				 -> (setTabulation "<br>\n" howMuchTab) ^ (readHList t howMuchTab)
		
		| HListElement(insideList) :: t  -> (setTabulation "<li>" howMuchTab) ^ (readHListElement insideList howMuchTab true) ^ "</li>\n" ^ (readHList t howMuchTab)
		
		| elem :: t                      -> (setTabulation "<li>" howMuchTab) ^ (readHListElement [elem] howMuchTab true) ^ "</li>\n" ^ (readHList t howMuchTab)

	
	in let rec readElement hlst = 
	
		match hlst with
		| [] 												   -> ""
		
		| NewLineChar :: t 									   -> "<br>\n" ^ (readElement t)
		
		| Heading(number, text) :: t when (checkNumber number) -> "<h" ^ (string_of_int number) ^ ">" ^ (readTable text "") ^ "</h" ^ (string_of_int number) ^ ">\n" ^ (readElement t)
		
		| Paragraph(text) :: t 								   -> "<p>" ^ (readTable text "") ^ "</p>\n" ^ (readElement t) 
		
		| OrderedList(olist) :: t    when olist != []   	   -> "<ol>\n" ^ (readHList olist 1) ^ "</ol>\n" ^ (readElement t)
		
		| UnorderedList(ulist) :: t  when ulist != []          -> "<ul>\n" ^ (readHList ulist 1) ^ "</ul>\n" ^ (readElement t)
		
		| _ :: t 											   -> readElement t
			

	in match lst with
		| [] -> print_string (readTitle hTitle)
		
		| _  -> print_string ("\n<!DOCTYPE html>\n<html>\n" ^ (readTitle hTitle) ^  "<body>\n\n" ^ (readElement lst) ^ "\n</body>\n</html>\n");;
	


let milkLactose = [TextArea(["With lactose"]); TextArea(["Without lactose"]); NewLineChar]

let drinkTypes = [TextArea(["Tea"]); TextArea(["Milk"]); TextArea(["Coffee"])];;

let milkPercent = [NewLineChar; TextArea(["3.2%"]); TextArea(["2.0%"]); TextArea(["0.5%"]); NewLineChar];; 

let milkTypes = [HListElement([TextArea(["Whole"; "milk"]); UnorderedList(milkPercent); UnorderedList(milkLactose)]); TextArea(["Soy milk"]); TextArea(["Almond milk"])];;

let coffeeTypes = [HListElement([TextArea(["With milk"]); UnorderedList(milkTypes)]); TextArea(["With sugar"]); TextArea(["With milk and sugar"])];;

let orderedDrinks = [TextArea(["Tea"]); TextArea(["Milk"]); HListElement([TextArea(["Coffee"]); OrderedList(coffeeTypes)])];;

let unorderedDrinks = [TextArea(["Tea"]); TextArea(["Milk"]); HListElement([TextArea(["Coffee"]); UnorderedList(coffeeTypes)])];;

let opcje = [TextArea(["First option"]); TextArea(["Second option"]); TextArea(["Third option"])];;

let listHTML1 = [NewLineChar; Heading(7, ["First Heading"]); Paragraph(["First paragraph"]); OrderedList(drinkTypes); UnorderedList(drinkTypes)];;

let listHTML2 = [Heading(1, ["First"; "Heading"]); Paragraph(["First paragraph"]); OrderedList(unorderedDrinks); UnorderedList(orderedDrinks)];;

let listHTML3 = [Heading(1, ["First Heading"]); Paragraph(["First paragraph"]); OrderedList([HListElement([Heading(3, ["First Heading"]); Paragraph(["First paragraph"])]); HListElement([Heading(3, ["Second Heading"]); Paragraph(["Second paragraph"])]); HListElement([Heading(3, ["Third Heading"]); Paragraph(["Third paragraph"]); UnorderedList(opcje)])])];;

let title1 = Title("Page Title 1");;

let title2 = Title("");;

build title1 listHTML1;;

build title2 listHTML2;;

build title1 listHTML3;;

build title1 [Paragraph(["Paragraph"])];;

build Empty [Paragraph(["Paragraph"])];;

build title1 [Heading(70, ["Heading 70"]); Heading(1, ["Heading 1"])];;

















































































OCaml

type h_Element = NewLineChar
 | Heading of int * string list
 | Paragraph of string list
 | TextArea of string list
 | HListElement of h_Element list
 | OrderedList of h_Element list
 | UnorderedList of h_Element list;;
 
type h_Title =
 | Title of string;;

let build hTitle lst = 

	let checkNumber number = (number >= 1 && number <= 6) in

	let rec setTabulation tag howMuch =
		match howMuch with
		| 0 -> tag
		| _ -> setTabulation ("\t" ^ tag) (howMuch-1)
		
	
	in let rec readTable table nextTag =
		match table with 
		| []     -> ""
		| h :: t -> nextTag ^ h ^ (readTable t "<br>")
		

	in let readTitle title = 
		match title with 
		| Title("")   -> ""
		| Title(text) -> "<head>\n\n<title>" ^ text ^ "</title>\n\n</head>\n"


	in let rec readHList hlst howMuchTab =
	
		let nestedList beginTag endTag lst howMuchTab = "\n" ^ (setTabulation beginTag howMuchTab) ^ (readHList lst (howMuchTab+1)) ^ (setTabulation endTag howMuchTab) in

		let rec readHListElement hlst howMuchTab onlyTextArea = 
			match hlst with
			| [] 													 -> if onlyTextArea then "" else "\n" ^ (setTabulation "" howMuchTab)
			
			| NewLineChar :: t									     -> "\n" ^ (setTabulation "<br>" (howMuchTab+1)) ^ (readHListElement t howMuchTab false) 
			
			| TextArea(text) :: TextArea(_) :: _				     -> (readTable text "") ^ " " ^ (readHListElement (List.tl hlst) howMuchTab onlyTextArea)
			
			| TextArea(text) :: t				   					 -> if onlyTextArea then (readTable text "") ^ (readHListElement t howMuchTab onlyTextArea)	
																		else "\n" ^ (setTabulation (readTable text "") (howMuchTab+1)) ^ (readHListElement t howMuchTab onlyTextArea)
																		
			| Paragraph(text) :: t                                   -> "\n" ^ (setTabulation "<p>" (howMuchTab+1)) ^ (readTable text "") ^ "</p>" ^ (readHListElement t howMuchTab false)
			
			| Heading(number, text) :: t when (checkNumber number)   -> "\n" ^ (setTabulation ("<h" ^ (string_of_int number) ^ ">") (howMuchTab+1)) ^ (readTable text "") ^ "</h" ^ (string_of_int number) ^ ">" ^ (readHListElement t howMuchTab false)
			
			| OrderedList(olist) :: t    when olist != []            -> (nestedList "<ol>\n" "</ol>" olist (howMuchTab + 1)) ^ (readHListElement t howMuchTab false)
			
			| UnorderedList(ulist) :: t  when ulist != []            -> (nestedList "<ul>\n" "</ul>" ulist (howMuchTab + 1)) ^ (readHListElement t howMuchTab false)
			
			| _ :: t                                                 -> readHListElement t howMuchTab onlyTextArea
		
		in match hlst with
		| [] 							 -> ""
		
		| NewLineChar :: t 				 -> (setTabulation "<br>\n" howMuchTab) ^ (readHList t howMuchTab)
		
		| HListElement(insideList) :: t  -> (setTabulation "<li>" howMuchTab) ^ (readHListElement insideList howMuchTab true) ^ "</li>\n" ^ (readHList t howMuchTab)
		
		| _ :: t                         -> readHList t howMuchTab 

	
	in let rec readElement hlst = 
	
		match hlst with
		| [] 												   -> ""
		
		| NewLineChar :: t 									   -> "<br>\n" ^ (readElement t)
		
		| Heading(number, text) :: t when (checkNumber number) -> "<h" ^ (string_of_int number) ^ ">" ^ (readTable text "") ^ "</h" ^ (string_of_int number) ^ ">\n" ^ (readElement t)
		
		| Paragraph(text) :: t 								   -> "<p>" ^ (readTable text "") ^ "</p>\n" ^ (readElement t) 
		
		| OrderedList(olist) :: t    when olist != []   	   -> "<ol>\n" ^ (readHList olist 1) ^ "</ol>\n" ^ (readElement t)
		
		| UnorderedList(ulist) :: t  when ulist != []          -> "<ul>\n" ^ (readHList ulist 1) ^ "</ul>\n" ^ (readElement t)
		
		| _ :: t 											   -> readElement t
			

	in match lst with
		| [] -> print_string (readTitle hTitle)
		
		| _  -> print_string ("\n<!DOCTYPE html>\n<html>\n" ^ (readTitle hTitle) ^  "<body>\n\n" ^ (readElement lst) ^ "\n</body>\n</html>\n");;
	

let milkLactose = [HListElement([TextArea(["With lactose"])]); HListElement([TextArea(["Without lactose"])]); NewLineChar]

let drinkTypes = [HListElement([TextArea(["Tea"])]); HListElement([TextArea(["Milk"])]); HListElement([TextArea(["Coffee"])])];;

let milkPercent = [NewLineChar; HListElement([TextArea(["3.2%"])]); HListElement([TextArea(["2.0%"])]); HListElement([TextArea(["0.5%"])]); NewLineChar];; 

let milkTypes = [HListElement([TextArea(["Whole"; "milk"]); UnorderedList(milkPercent); UnorderedList(milkLactose)]); HListElement([TextArea(["Soy milk"])]); HListElement([TextArea(["Almond milk"])])];;

let coffeeTypes = [HListElement([TextArea(["With milk"]); UnorderedList(milkTypes)]); HListElement([TextArea(["With sugar"])]); HListElement([TextArea(["With milk and sugar"])])];;

let orderedDrinks = [HListElement([TextArea(["Tea"])]); HListElement([TextArea(["Milk"])]); HListElement([TextArea(["Coffee"]); OrderedList(coffeeTypes)])];;

let unorderedDrinks = [HListElement([TextArea(["Tea"])]); HListElement([TextArea(["Milk"])]); HListElement([TextArea(["Coffee"]); UnorderedList(coffeeTypes)])];;

let opcje = [HListElement([TextArea(["First option"])]); HListElement([TextArea(["Second option"])]); HListElement([TextArea(["Third option"])])];;

let listHTML1 = [NewLineChar; Heading(7, ["First Heading"]); Paragraph(["First paragraph"]); OrderedList(drinkTypes); UnorderedList(drinkTypes)];;

let listHTML2 = [Heading(1, ["First"; "Heading"]); Paragraph(["First paragraph"]); OrderedList(unorderedDrinks); UnorderedList(orderedDrinks)];;

let listHTML3 = [Heading(1, ["First Heading"]); Paragraph(["First paragraph"]); OrderedList([HListElement([Heading(3, ["First Heading"]); Paragraph(["First paragraph"])]); HListElement([Heading(3, ["Second Heading"]); Paragraph(["Second paragraph"])]); HListElement([Heading(3, ["Third Heading"]); Paragraph(["Third paragraph"]); UnorderedList(opcje)])])];;

let title1 = Title("Page Title 1");;

let title2 = Title("");;

build title1 listHTML1;;

build title2 listHTML2;;

build title1 listHTML3;;

build title1 [Paragraph(["Paragraph"])];;

build title1 [Heading(70, ["Heading 70"]); Heading(1, ["Heading 1"])];;













































