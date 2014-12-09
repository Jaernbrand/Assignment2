(defn safeFun [form] 
	(let [returnValue form] 
		(if (instance? java.io.Closeable form)
			(.close form)
		)
	returnValue
	)
)

(safeFun (let [s (new Integer 5)] (.toString s)))
