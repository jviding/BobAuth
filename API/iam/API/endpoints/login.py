from django.http import HttpResponse, HttpResponseNotFound
from django.views.decorators.http import require_http_methods
from django.contrib import auth
import json

@require_http_methods(["POST"])
def login(request):
    # TODO: JSON validation
    # TODO: Login CSRF protection (X is logged in as Y by malicious site)
    # If is_authenticated then don't login user
    username = json.loads(request.body)['username']
    password = json.loads(request.body)['password']
    user = auth.authenticate(username=username, password=password)
    if user is not None:
        auth.login(request, user)
        return HttpResponse("Success")
    else:
        return HttpResponseNotFound("Failure")
