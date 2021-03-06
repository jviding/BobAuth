from django.http import JsonResponse, HttpResponseNotFound
from django.views.decorators.http import require_http_methods
from django.contrib import auth
import json

@require_http_methods(["POST"])
def login(request):
    # TODO: JSON validation
    # TODO: Login CSRF protection (X is logged in as Y by malicious site)
    # If is_authenticated then don't login user
    # TODO: Brute force protection (Captcha to front-end app?)
    username = json.loads(request.body)['username']
    password = json.loads(request.body)['password']
    user = auth.authenticate(username=username, password=password)
    if user is not None:
        auth.login(request, user)
        return JsonResponse({
            "userID": user.id,
            "username": user.username,
            "email": user.email,
            "isAdmin": user.is_superuser
        })
    else:
        return HttpResponseNotFound("Failure")
