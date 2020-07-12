from django.http import HttpResponse, HttpResponseForbidden
from django.views.decorators.http import require_http_methods
from django.contrib.auth.models import User
from django.contrib import auth
import json

@require_http_methods(["POST"])
def signup(request):
    # TODO: JSON validation
    # TODO: Registration CSRF protection
    # TODO: Ensure email is unique and valid email
    # TODO: Ensure quality password
    # TODO: Ensure username minimum length
    # TODO: Handle errors
    username = json.loads(request.body)['username']
    password = json.loads(request.body)['password']
    email = json.loads(request.body)['email']
    if username and password and email:
        user = User.objects.create_user(username=username, email=email, password=password)
        auth.login(request, user)
        return HttpResponse("Success")
    else:
        return HttpResponseForbidden("Failure")
