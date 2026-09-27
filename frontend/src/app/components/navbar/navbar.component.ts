import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { CartService } from '../../core/services/cart.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
})
export class NavbarComponent {
  constructor(
    public authService: AuthService,
    public cartService: CartService,
    private router: Router
  ) {
    if (this.authService.isLoggedIn()) {
      this.cartService.refresh();
    }
  }

  logout(): void {
    this.authService.logout();
    this.cartService.clear().subscribe();
    this.router.navigate(['/login']);
  }
}
