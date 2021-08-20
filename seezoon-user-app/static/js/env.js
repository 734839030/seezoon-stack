export  function getBaseUrl() {
	if (process.env.NODE_ENV === 'development') {
		return 'http://127.0.0.1:8080';
	} else if (process.env.NODE_ENV === 'production') {
		return 'https://stack.seezoon.com/api';
	}
}