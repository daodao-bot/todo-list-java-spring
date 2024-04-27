(function () {

    function alert(message) {
        document.querySelector("#alert").innerText = message;
        setTimeout(() => {
            document.querySelector("#alert").innerText = "";
        }, 3000);
    }

    function all() {
        return fetch("/api/todo")
            .then(response => response.json())
            .then(data => {
                return data;
            })
            .catch(
                error => {
                    alert("Error: " + error);
                }
            )
    }

    function get(id) {
        return fetch("/api/todo/" + id)
            .then(response => response.json())
            .then(data => {
                return data;
            })
            .catch(
                error => {
                    alert("Error: " + error);
                }
            )
    }

    function add(data) {
        return fetch("/api/todo", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data),
        })
            .then(response => response.json())
            .then(data => {
                return data;
            })
            .catch(
                error => {
                    alert("Error: " + error);
                }
            )
    }

    function put(id, data) {
        return fetch("/api/todo/" + id, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data),
        })
            .then(response => response.json())
            .then(data => {
                return data;
            })
            .catch(
                error => {
                    alert("Error: " + error);
                }
            )
    }

    function del(id) {
        return fetch("/api/todo/" + id, {
            method: "DELETE",
        })
            .then(data => {
                return data;
            })
            .catch(
                error => {
                    alert("Error: " + error);
                }
            )
    }

    function render() {
        all().then(data => {
            const list = document.querySelector("#list");
            list.innerHTML = "";
            data.forEach(item => {
                list.innerHTML += `
          <div>
            <input type="hidden" value="${item.id}">
            <input type="checkbox" class="done" ${item.done ? "checked" : ""}/>
            <input type="text" value="${item.name}" readonly>
            <button class="del">-</button>
          </div>
          `;
            });
        });
    }

    document.querySelector("#add").addEventListener("click", () => {
        const name = document.querySelector("#name").value;
        const done = document.querySelector("#done").checked;
        add({name, done}).then(r => {
            render()
        });
    });

    document.querySelector("#list")?.addEventListener("click", (event) => {
        const target = event.target;
        if (target.className === "done") {
            let id = target.parentElement.querySelector("input[type=hidden]").value;
            const name = target.parentElement.querySelector("input[type=text]").value;
            const done = target.checked;
            id = parseInt(id);
            put(id, {id, name, done}).then(r => {
                render()
            });
        } else if (target.className === "del") {
            let id = target.parentElement.querySelector("input[type=hidden]").value;
            id = parseInt(id);
            del(id).then(r => {
                render()
            });
        }
    });

    render();
})
();