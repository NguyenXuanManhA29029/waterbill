import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'ho-gia-dinh',
        data: { pageTitle: 'HoGiaDinhs' },
        loadChildren: () => import('./ho-gia-dinh/ho-gia-dinh.module').then(m => m.HoGiaDinhModule),
      },
      {
        path: 'tai-khoan',
        data: { pageTitle: 'TaiKhoans' },
        loadChildren: () => import('./tai-khoan/tai-khoan.module').then(m => m.TaiKhoanModule),
      },
      {
        path: 'thue',
        data: { pageTitle: 'Thues' },
        loadChildren: () => import('./thue/thue.module').then(m => m.ThueModule),
      },
      {
        path: 'bac-tien-ho-ngheo',
        data: { pageTitle: 'BacTienHoNgheos' },
        loadChildren: () => import('./bac-tien-ho-ngheo/bac-tien-ho-ngheo.module').then(m => m.BacTienHoNgheoModule),
      },
      {
        path: 'bac-tien-ho-thuong',
        data: { pageTitle: 'BacTienHoThuongs' },
        loadChildren: () => import('./bac-tien-ho-thuong/bac-tien-ho-thuong.module').then(m => m.BacTienHoThuongModule),
      },
      {
        path: 'hoa-don',
        data: { pageTitle: 'HoaDons' },
        loadChildren: () => import('./hoa-don/hoa-don.module').then(m => m.HoaDonModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
