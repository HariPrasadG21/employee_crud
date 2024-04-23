    function fetchWithToken(url, options = {}) {
    // Get the token from local storage
    const token = localStorage.getItem('token');

    // Add the Authorization header to the headers option
    options.headers = options.headers || {};
    options.headers['Authorization'] = 'Bearer ' + token;

    // Call the fetch API with the url and options
    return fetch(url, options);
}