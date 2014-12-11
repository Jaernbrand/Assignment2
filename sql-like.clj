(def <> "notEqual") ;;definition

;;from
(defmacro from [table]
	`(if (set? ~table)
		(do ~table)
		(throw (Exception. "Table is not a set!"))
	)
)

;;extractRowValues
(defn extractRowValues [row selKeys]
	(let [extRow {}] 
		(if ( > (count selKeys) 1)
			(conj
				(assoc extRow (first selKeys) (get row (first selKeys)))	
				(conj extRow (extractRowValues row (rest selKeys)))
			)
			
			(assoc extRow (first selKeys) (get row (first selKeys)))	
		)
	)	
)

;;extractColumns
(defn extractColumns [table selKeys]
	(if ( > (count table) 1) 
		(set 
			(concat
				(conj #{} (extractRowValues (first table) selKeys))
				(extractColumns (rest table) selKeys)
			)
		)

		(conj #{} (extractRowValues (first table) selKeys))
	)
)

;;orderby
(defn orderby [table orderCol]
	(sort-by orderCol table)
)

;;where
(defn where [kw op limit table] ;;keyword operator limit table
	(if(= op "notEqual") 				 ;;(filterByValue :id < 3 persons)
		(println (filter #(not=(kw %) limit) table) )
		(println (filter #(op (kw %) limit) table) )
	)
)	

;;select
(defmacro select ([columns from table]
		`(let [tbl# (~from ~table)]
			(extractColumns tbl# ~columns)
		)
	) 
	([columns from table where col opList ordr ordrCol]
		`(let [tbl# (~from ~table)]
			(do 
				(~ordr (extractColumns tbl# ~columns) ~ordrCol)
			)
		)
	)
)
