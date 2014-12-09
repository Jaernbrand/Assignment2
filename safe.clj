(defmacro safe [variable form]
	)

(import java.io.FileReader java.io.File)
(safe [s (new FileReader (new File))])
