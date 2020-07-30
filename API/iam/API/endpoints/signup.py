from django.http import JsonResponse, HttpResponseForbidden
from django.views.decorators.http import require_http_methods
from django.contrib.auth.models import User
from django.contrib import auth
import json

@require_http_methods(["POST"])
def signup(request):

    username = json.loads(request.body)['username']
    password = json.loads(request.body)['password']
    email = json.loads(request.body)['email']

    if username and password and email:
        user = User.objects.create_user(username=username, email=email, password=password)
        auth.login(request, user)
        return JsonResponse({})
    else:
        return HttpResponseForbidden("Failure")
