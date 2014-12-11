(def persons #{
	{:id 1 :name "olle"} 
	{:id 2 :name "anna"} 
	{:id 3 :name "isak"} 
	{:id 4 :name "beatrice"}
})

(load-file "sql-like.clj")

(println (select [:id :name] from persons))
(println (select [:id :name] from persons orderby :id))
(println (select [:id :name] from persons orderby :name))
(println (select [:id :name] from persons where :id (> 2) orderby :name))
