(defmacro from [table]
	`(if (set? ~table)
		(do ~table)
		(throw (Exception. "Table is not a set!"))
	)
)

(defn extractRowValues [row keys]
	(let [extRow {}] 
		(if ( > (count keys) 1)
			(conj
				(assoc extRow (first keys) (get row (first keys)))	
				(conj extRow (extractRowValues row (rest keys)))
			)
			
			(assoc extRow (first keys) (get row (first keys)))	
		)
	)	
)

(defn extractColumns [table & keys]
	(let [retTable# #{}]
		(conj retTable# (extractRowValues (first table) keys))
		(if ( > (count table) 1) 
			(extractColumns (rest table))
		)
	retTable#
	)
)

(defmacro select [keys from table & args]
	`(let [t# (~from ~table)]
		(extractColumns t#, keys)
	)
)

