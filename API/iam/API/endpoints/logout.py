from django.http import JsonResponse
from django.views.decorators.http import require_http_methods
from django.contrib import auth

@require_http_methods(["POST"])
def logout(request):
    auth.logout(request)
    return JsonResponse({})
