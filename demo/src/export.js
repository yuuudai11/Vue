// 関数を定義します。
function func1(prm) {
  console.log(prm);
  alert(prm);
}

// 別の関数を定義します。
function func2() {
  console.log("Good afternoon!");
  alert('func2 !!!');
}

/**
 * Sends an AJAX POST request to a specified URL with the provided data.
 *
 * @param {string} url - The URL to which the request is sent.
 * @param {Object} data - The data to be sent in the request body.
 * @param {function} callback - A callback function that is called when the request is complete.
 * @param {function} [errorCallback] - An optional callback function that is called if the request fails.
 */
function sendPostRequest(url, data, callback, errorCallback) {
    const xhr = new XMLHttpRequest();
  	xhr.open('POST', url, true);
    xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
    xhr.onload = function() {
        if (xhr.status >= 200 && xhr.status < 300) {
            // Call the callback function with the response data
            callback(JSON.parse(xhr.responseText));
        } else {
            // If an error callback is provided, call it with the status text
            if (errorCallback) {
                errorCallback(xhr.statusText);
            } else {
                console.error('Request failed with status:', xhr.statusText);
            }
        }
    };
    xhr.onerror = function() {
        if (errorCallback) {
            errorCallback('Network Error');
        } else {
            console.error('Network Error');
        }
    };
    xhr.send(JSON.stringify(data));
}

async function sendPostRequestB(url, data) {
  alert('001');
  try {
    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
    });
    alert('002');
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    alert('003');
    const result = await response.json();
    console.log('Response:', result);
    return result;
  } catch (error) {
    console.error('Error:', error);
  }
  alert('004');
}


// 関数をエクスポートします。
export default {
  func1,
  func2,
  sendPostRequest,
  sendPostRequestB,
};
