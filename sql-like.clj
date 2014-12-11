(defmacro from [table]
	`(if (set? ~table)
		(do ~table)
		(throw (Exception. "Table is not a set!"))
	)
)

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

(defn orderby [table orderCol]
	(sort-by orderCol table)
)

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
